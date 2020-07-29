package com.victolee.sampleproject.handler.api;

import java.io.IOException;  
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.victolee.sampleproject.exception.APIException;
import com.victolee.sampleproject.handler.AbstractAPIHandler;
import com.victolee.sampleproject.handler.HandlerManager;
import com.victolee.sampleproject.handler.api.user.service.UserInfoService;
import com.victolee.sampleproject.helper.ResponseHelper;

/*
* ApiHandler Class
* Common API ì²˜ë¦¬ Handler
* @author  Pakerbb
* @version 1.0
*/

@Component("api")
public class ApiHandler extends AbstractAPIHandler {

	private Logger logger = LogManager.getLogger(this.getClass());

	public ApiHandler() {
		logger.info("############### Create ApiHandler ###############");
	}

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private HandlerManager handlerManager;

	@Override
	public String getPid() {
		return "COMMON";
	}

	@Override
	public String getServiceName() {
		return "Api";
	}

	@Override 
	// 500¿¡·¯ ÄÚµå
	public String getErrorCode() {
		return "500";
	}

	@Override
	public Object handle(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			Map<String, Object> request) throws Exception {

		String apiName = (String) request.get("apiName");
		Map<String, Object> header = (Map<String, Object>) request.get("header");
		Map<String, Object> body = (Map<String, Object>) request.get("body");

		switch (apiName) {
		case "selectUserInfo":
			return selectUserInfo(request);
		case "insertUserInfo":
			return insertUserInfo(request);
		case "updateUserInfo":
			return updateUserInfo(request);
		case "deleteUserInfo":
			return deleteUserInfo(request);
		default:
			throw new APIException("NOTAPI");
		}
	}

	/*
	 * UserDelete API
	 */
	private Object deleteUserInfo(Map<String, Object> request) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * UserUpdate API
	 */
	private Object updateUserInfo(Map<String, Object> request) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * UserInsert API
	 */
	private Object insertUserInfo(Map<String, Object> request) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * UserSelect API
	 */
	private List<Map<String, Object>> selectUserInfo(Map<String, Object> request) throws Exception {
		
		return userInfoService.selectUserInfo(request);
	}
}