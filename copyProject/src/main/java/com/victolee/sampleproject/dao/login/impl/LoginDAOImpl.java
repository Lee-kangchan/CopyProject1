package com.victolee.sampleproject.dao.login.impl;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.victolee.sampleproject.dao.login.LoginDAO;
import com.victolee.sampleproject.mapper.LoginMapper;
import com.victolee.sampleproject.model.UserInfo;


public class LoginDAOImpl implements LoginDAO{

	LoginMapper loginMapper;
	SqlSession sqlSession = null;
	
	public LoginDAOImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
		//sqlSession의 Mapper 인터페이스를 가져온다.
		loginMapper = sqlSession.getMapper(LoginMapper.class);
	}

	@Override
	public UserInfo selectUserInfo(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectLoginIdExistCheck(Map<String, Object> params) {
				Map<String, Object> param = params;
				param.put("queryId", "selectLoginIdExistCheck");
		    	return loginMapper.selectLoginIdExistCheck(param);
	}

}
