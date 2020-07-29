package com.victolee.sampleproject.service.login;

import java.util.Map;

import com.victolee.sampleproject.helper.APIResponse;
import com.victolee.sampleproject.utils.RequestInfo;

public interface LoginService {

	APIResponse selectUserInfo(RequestInfo requestInfo, Map<String, Object> request, String serverAddr) throws Exception;
	
	public String makeAuthTokenAndHashKey(int len, String paramChars);

	boolean loginIdExistCheck(Map<String, Object> params) throws Exception;
}
