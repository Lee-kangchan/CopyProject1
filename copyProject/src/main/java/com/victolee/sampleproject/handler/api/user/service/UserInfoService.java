package com.victolee.sampleproject.handler.api.user.service;

import java.util.List;
import java.util.Map;

public interface UserInfoService {

	List<Map<String, Object>> selectUserInfo(Map<String, Object> request) throws Exception;

}
