package com.victolee.sampleproject.mapper;

import java.util.Map;

import com.victolee.sampleproject.model.UserInfo;

public interface LoginMapper {
	public UserInfo selectUserInfo(Map<String,Object> paramMap);

	public String selectLoginIdExistCheck(Map<String, Object> param);
}
