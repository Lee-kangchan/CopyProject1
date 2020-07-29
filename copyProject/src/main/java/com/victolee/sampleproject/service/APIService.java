package com.victolee.sampleproject.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victolee.sampleproject.constant.Constant;
import com.victolee.sampleproject.exception.APIException;
import com.victolee.sampleproject.handler.APIHandler;
import com.victolee.sampleproject.handler.HandlerManager;
import com.victolee.sampleproject.helper.ResponseHelper;
import com.victolee.sampleproject.helper.APIResponse;
import com.victolee.sampleproject.search.CustomSearchClient;
import com.victolee.sampleproject.search.RedisManager;

/*
	* APIService Class
	* 진입 시점 elasticsearch / redis setting 및 Handler 분기
	* @author  Pakerbb
	* @version 1.0
*/

@Service("aPIService")
public class APIService implements InitializingBean {
	@Autowired
	private HandlerManager handlerManager;

	@Autowired
	private CustomSearchClient esClient;

	@Autowired
	private RedisManager redisManager;

	private Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * Object가 생성되고 DI 작업까지 마친다음 실행되는 메서드 InitializingBean 인터페이스를 통한 초기화 시점 호출
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}

	/**
	 * redis / elasticsearch connection / setting
	 */

	public void init() {
		long time = System.currentTimeMillis();
		try {
			logger.info("## APIService.init-start ##");
			// 공통 Redis 데이터 조회
			Map<String, String> redisInfoData = redisManager.getRedisInfoList();
			// ElasticSearch Create Index / Setting / Mapping 생성
			esClient.InitializeCust(redisInfoData);
			time = System.currentTimeMillis() - time;
			logger.info("APIService.init - end ET[" + time + "]");
		} catch (Exception e) {
			logger.error("APIService.init-error. msg=" + e.getLocalizedMessage());
		}
	}

	/**
	 * ServiceName에 따라 handler 분기 처리
	 * 
	 * @param servletRequest
	 * @param servletResponse
	 * @param request
	 * @param serviceName
	 * @param apiName
	 * @return APIResponse response
	 */
	public Object actionService(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			Map<String, Object> request, String serviceName) {
		long time = System.currentTimeMillis();
		String apiName = (String) request.get("apiName");
		APIHandler handler = handlerManager.get(serviceName);
		APIResponse response = null;
		if (handler == null) {
			response = new APIResponse();
			response.setResultCode("SER001");
			response.setResultMessage("invalid service : " + serviceName);
			return response;
		}
		try {
			Map<String, Object> header = (Map<String, Object>) request.get("header");
			Map<String, Object> body = (Map<String, Object>) request.get("body");
			logger.info(serviceName + "." + apiName + "-start " + request);
			Object result = handler.handle(servletRequest, servletResponse, request);
			response = ResponseHelper.createSuccess(result);
			time = System.currentTimeMillis() - time;
			logger.info(serviceName + "." + apiName + "-end ET[" + time + "] " + request + response);
		} catch (APIException ae) {

			time = System.currentTimeMillis() - time;
			logger.error(serviceName + "." + apiName + "-error ET[" + time + "] " + request + response + ae);
		} catch (Exception e) {
			time = System.currentTimeMillis() - time;
			logger.error(serviceName + "." + apiName + "-error ET[" + time + "] " + request + response + e);
		}
		return response;
	}
}
