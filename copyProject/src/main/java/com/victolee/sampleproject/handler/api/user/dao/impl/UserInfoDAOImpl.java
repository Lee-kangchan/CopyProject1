package com.victolee.sampleproject.handler.api.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.victolee.sampleproject.handler.api.user.dao.UserInfoDAO;
import com.victolee.sampleproject.handler.api.user.mapper.UserInfoMapper;

public class UserInfoDAOImpl implements UserInfoDAO{
	
	SqlSession sqlSession = null;
	UserInfoMapper userInfoMapper;
	
	public UserInfoDAOImpl(SqlSession sqlSession) {
		userInfoMapper = sqlSession.getMapper(UserInfoMapper.class);
	}

	public List<Map<String, Object>> selectUserInfo(Map<String, Object> request) {
		// TODO Auto-generated method stub
		request.put("queryId", "selectUserInfo");
		return userInfoMapper.selectUserInfoList(request);
	}
	
}
