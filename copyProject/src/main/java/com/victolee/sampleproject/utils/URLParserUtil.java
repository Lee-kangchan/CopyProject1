package com.victolee.sampleproject.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import com.victolee.sampleproject.constant.CommonConstants;

public class URLParserUtil {
	public static String getContextPath(HttpServletRequest req) {
		String contextPath = "";

		String cPath = req.getRequestURI();
		
		if (cPath.contains(CommonConstants.GET_TOKEN)) {
			contextPath = CommonConstants.GET_TOKEN;
		}
		else {
			contextPath = req.getContextPath();
		}

		contextPath = contextPath.replaceAll("/", "");
		return contextPath;
	}
	
	public static String getApiCode(HttpServletRequest req) {
		String servletPath = req.getServletPath();
		if (servletPath == null)
			return "";

		String servletArr[] = servletPath.split("/");
		if (servletArr.length == 0)
			return "";

		return servletArr[0];
	}
	
	public static String getUTF8QueryString(HttpServletRequest req) throws UnsupportedEncodingException {
		String param = req.getQueryString();
		param = CommonUtil.nullCheck(param);
		if (!param.isEmpty() && param.indexOf("url=") == 0) {
			param = URLDecoder.decode(param, "UTF-8");
			param = param.substring(4);
		}
		
		return param;
	}
}
