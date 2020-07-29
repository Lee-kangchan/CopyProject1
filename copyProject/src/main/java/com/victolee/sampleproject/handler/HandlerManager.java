package com.victolee.sampleproject.handler;


import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/*
* HandlerManager Class
* ServiceName을 통해 분기 처리 작업을 도와주는 class
* @author  Pakerbb
* @version 1.0
*/

@Component("handlerManager")
public class HandlerManager implements InitializingBean {
	
	@Autowired
	ApplicationContext applicationContext; 
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	private Map<String, APIHandler> handlerMap = new HashMap<String, APIHandler>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, APIHandler> beansMap = applicationContext.getBeansOfType(APIHandler.class);
		for (APIHandler handler : beansMap.values()) {
			handlerMap.put(handler.getServiceName(), handler);
			logger.info("register handler : serviceName=" + handler.getServiceName() + ", className=" + handler.getClass().getName());
		}
	}
	
	public APIHandler get(String serviceName) {
		return handlerMap.get(serviceName);
	}
}


