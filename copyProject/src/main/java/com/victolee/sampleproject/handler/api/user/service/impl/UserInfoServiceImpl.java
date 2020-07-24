package com.victolee.sampleproject.handler.api.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victolee.sampleproject.handler.api.user.dao.UserInfoDAO;
import com.victolee.sampleproject.handler.api.user.dao.impl.UserInfoDAOImpl;
import com.victolee.sampleproject.handler.api.user.service.UserInfoService;


@Service("UserInfoService")
public class UserInfoServiceImpl implements UserInfoService {
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<Map<String, Object>> selectUserInfo(Map<String, Object> request) throws Exception {
		UserInfoDAO userInfoDAO = new UserInfoDAOImpl(sqlSession);
		return userInfoDAO.selectUserInfo(request);
	}

}
