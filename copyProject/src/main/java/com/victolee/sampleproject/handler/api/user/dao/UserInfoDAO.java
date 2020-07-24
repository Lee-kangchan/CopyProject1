package com.victolee.sampleproject.handler.api.user.dao;

import java.util.List;
import java.util.Map;

public interface UserInfoDAO {

	List<Map<String, Object>> selectUserInfo(Map<String, Object> request);
}
