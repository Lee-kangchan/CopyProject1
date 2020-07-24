package com.victolee.sampleproject.handler.api.user.mapper;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper {
	
	List<Map<String, Object>> selectUserInfoList(Map<String,Object> request);
}
