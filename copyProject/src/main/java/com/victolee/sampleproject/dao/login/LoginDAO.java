package com.victolee.sampleproject.dao.login;

import java.util.Map;

import com.victolee.sampleproject.model.UserInfo;


public interface LoginDAO {

	public UserInfo selectUserInfo(Map<String, Object> paramMap) throws Exception;

	public String selectLoginIdExistCheck(Map<String, Object> params);

}
