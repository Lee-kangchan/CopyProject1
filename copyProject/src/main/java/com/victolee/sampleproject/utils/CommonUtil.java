package com.victolee.sampleproject.utils;

import java.util.Random; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.victolee.sampleproject.constant.CommonConstants;

public class CommonUtil {
	/**
	 * CORS 처리를 위한 설정
	 * 
	 * @param request
	 * @param response
	 */
	public static void setCORS(HttpServletRequest req, HttpServletResponse res) {

		String reqOrigin = req.getHeader("Origin");
		String reqHeader = req.getHeader("Access-Control-Request-Headers");
		String reqMethod = req.getHeader("Access-Control-Request-Method");

		if (!(CommonUtil.nullCheck(reqOrigin).isEmpty())) {
			res.setHeader("Access-Control-Allow-Origin", reqOrigin);

			if (!(CommonUtil.nullCheck(reqMethod).isEmpty()))
				res.setHeader("Access-Control-Allow-Methods", reqMethod);

			if (!(CommonUtil.nullCheck(reqHeader).isEmpty()))
				res.setHeader("Access-Control-Allow-Headers", reqHeader);

			res.setHeader("Access-Control-Max-Age", CommonConstants.CORS_PREFLIGHT_MAXAGE);
			res.setHeader("Access-Control-Allow-Credentials", CommonConstants.CORS_SUPPORT_CREDENTIALS);
		}
	}

	/**
	 * 문자열 null check
	 * 
	 * @return
	 */
	public static String nullCheck(String value) {
		if (value == null)
			value = "";
		return value;
	}

	/**
	 * 랜덤 문자열 30 대/소문자,숫자 포함
	 * 
	 * @return
	 */
	public static String getRandomString(int cipher, boolean includeNumber) {
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		int rndSel = 3;
		if (!includeNumber)
			rndSel = 2;

		for (int i = 0; i < cipher; i++) {
			int rIndex = rnd.nextInt(rndSel);
			switch (rIndex) {
			case 0:
				// a-z
				temp.append((char) ((int) (rnd.nextInt(26)) + 97));
				break;
			case 1:
				// A-Z
				temp.append((char) ((int) (rnd.nextInt(26)) + 65));
				break;
			case 2:
				// 0-9
				temp.append((rnd.nextInt(10)));
				break;
			}
		}
		return temp.toString();
	}

	public static String getServerAddr(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		String serverName = servletRequest.getServerName();
		int serverPort = servletRequest.getServerPort();
		String serverAddr = null;

		// http, https 구분
		if (servletRequest.isSecure()) {
			serverAddr = "https://";
		} else {
			serverAddr = "http://";
		}
		serverAddr += (serverName.equals("localhost") ? "127.0.0.1" : serverName)
				+ (serverPort == 80 ? "" : (serverPort == 8080 ? "" : ":" + serverPort));

		return serverAddr;
	}

	public static String passwordEncrypt(String userPassword) throws Exception {
		if (userPassword != null && !userPassword.equals("")) {
			return EgovFileScrty.encryptPassword(userPassword);
		} else {
			return "";
		}
	}
}
