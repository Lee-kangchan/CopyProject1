package com.victolee.sampleproject.service.login.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victolee.sampleproject.dao.login.LoginDAO;
import com.victolee.sampleproject.dao.login.impl.LoginDAOImpl;
import com.victolee.sampleproject.helper.APIResponse;
import com.victolee.sampleproject.model.SessionInfo;
import com.victolee.sampleproject.model.UserInfo;
import com.victolee.sampleproject.service.login.LoginService;
import com.victolee.sampleproject.utils.CommonUtil;
import com.victolee.sampleproject.utils.RequestInfo;

@Service("LoginService")

public class LoginServiceImpl implements LoginService {
	@Autowired
	SqlSession sqlSession;
	@Override
	public APIResponse selectUserInfo(RequestInfo requestInfo, Map<String, Object> request, String serverAddr) throws Exception {
		String authToken = makeAuthTokenAndHashKey(30, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz");
		String hashKey = makeAuthTokenAndHashKey(44, "0123456789");
		LoginDAO loginDAO = new LoginDAOImpl(sqlSession);

		APIResponse result = new APIResponse();

		String userPasswd = (String) request.get("password");

		request.put("password", CommonUtil.passwordEncrypt(replacePasswd(request.get("password").toString())));
		System.out.println("paramMap password : " + request.get("password"));
		UserInfo UserInfo = null;

    	
    	Map<String, Object> loginMp = new HashMap<String, Object>();
		loginMp.put("loginId", request.get("loginId"));
		loginMp.put("groupSeq", request.get("groupSeq"));
		
		// 아이디 존재 여부 확인 및 로그인 통제 옵션 값
    	boolean loginIdExistCheck = loginIdExistCheck(loginMp); 
    	
    	String loginId = null;
    	
		
    	if(loginIdExistCheck) { // 아이디가 존재
    		loginId = request.get("loginId") + "";
   
        	UserInfo = loginDAO.selectUserInfo(request);
        	
    	}else {
    		result.setResultMessage("아이디가 존재하지 않습니다.");	
    		result.setResultCode("500");
    	}

    	System.out.println("UserInfo : " + UserInfo);
    	
    	// 3. 결과를 리턴한다.
    	if(UserInfo != null && !UserInfo.getLoginId().equals("")){
    		
    		SessionInfo sessionInfo = new SessionInfo();
    		
    		sessionInfo.setUcUserInfo(UserInfo);
    		sessionInfo.setAuth_a_token(authToken);
    		sessionInfo.setHash_key(hashKey);
    		
    		Map<String, Object> sessionInfoMap = new HashMap<>();
    		sessionInfoMap.put("sessionInfo", sessionInfo);
    		
    		Map<String, Object> params = new HashMap<String, Object>();
	    	params.put("empSeq", UserInfo.getEmpSeq());
	    	params.put("groupSeq",UserInfo.getGroupSeq());
	 
    		result.setResultCode("200");
    		result.setResultMessage("로그인 완료되었습니다.");
    		result.setResult(sessionInfoMap);

    		return result;
    	} else {
			result.setResultMessage("아이디/비밀번호가 일치하지않습니다.");	
    		result.setResultCode("500");
    		return result;
    	}
	}

	@Override
	public boolean loginIdExistCheck(Map<String, Object> params) throws Exception {
		//DB 접근을 위한 DAO 객체 선언합니다.
		LoginDAO loginDAO = new LoginDAOImpl(sqlSession);	
		
		if(loginDAO.selectLoginIdExistCheck(params) != null) {
			return true;
		}else {
			return false;
		}
	}

	public String replacePasswd(String str){
		//&nbsp;
		if(str.indexOf("&nbsp;") != -1)
			str = str.replaceAll("&nbsp;", " ");
		if(str.indexOf("&amp;") != -1)
			str = str.replaceAll("&amp;", "&");
		if(str.indexOf("&lt;") != -1)
			str = str.replaceAll("&lt;", "<");
		if(str.indexOf("&gt;") != -1)
			str = str.replaceAll("&gt;", ">");
		if(str.indexOf("&quot;") != -1)
			str = str.replaceAll("&quot;", "\"");

		return str;
	}

	@Override
	public String makeAuthTokenAndHashKey(int len, String paramChars) {

		String chars = paramChars;
		int tokenLength = len;
		String randomstring = "";

		for (int i = 0; i < tokenLength; i++) {
			double rnum = Math.floor(Math.random() * chars.length());
			randomstring += chars.substring((int) rnum, (int) rnum + 1);
		}

		System.out.println(randomstring);
		return randomstring;
	}

}
