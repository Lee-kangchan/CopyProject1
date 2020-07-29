package com.victolee.sampleproject.handler.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.victolee.sampleproject.handler.AbstractAPIHandler;

@Component("web")
public class WebHandler extends AbstractAPIHandler{
	public WebHandler() {
		System.out.print("############### Create WebHandler ###############");
	}

	@Override
	public String getPid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return "Web";
	}

	@Override
	public String getErrorCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handle(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			Map<String, Object> request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
