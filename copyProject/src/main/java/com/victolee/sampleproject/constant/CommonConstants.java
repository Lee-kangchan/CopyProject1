package com.victolee.sampleproject.constant;

import java.util.Arrays;
import java.util.List;

public class CommonConstants {
	public static final String REQUEST_HEADER_X_FORWARDED_FOR = "X-Forwarded-For";
	public static final String REQUEST_HEADER_PROXY_CLIENT_IP = "Proxy-Client-IP";
	public static final String REQUEST_HEADER_WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
	public static final String REQUEST_HEADER_HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
	public static final String REQUEST_HEADER_HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
	public static final String REQUEST_HEADER_X_REAR_IP = "X-Real-IP";
	public static final String REQUEST_HEADER_X_REALIP = "X-RealIP";

	public static final String REQUEST_HEADER_TRANSACTION_ID = "transaction-id";
	public static final String REQUEST_HEADER_REFERER = "Referer";
	public static final String REQUEST_HEADER_USER_AGENT = "User-Agent";
	public static final String REQUEST_HEADER_TIMESTAMP = "timestamp";
	public static final String REQUEST_HEADER_CLIENT_ID = "client-id";

	public static final String REQUEST_HEADER_UNAUTH_SIGN = "signature";
	public static final String REQUEST_HEADER_AUTH_TOKEN = "Authorization";
	public static final int REDIS_UNAUTH_URL_DB = 11; 	// 비인증 허용 URL DB 및 byPass URL DB
	public static final int REDIS_UNAUTH_SIGN_DB = 12; 	// 비인증 사인 DB
	public static final int REDIS_AUTH_SIGN_DB = 13;     // 인증 사인 DB
	public static final String AUTH_TOKEN_TYPE = "AUTH";
	public static final String UNAUTH_TOKEN_TYPE = "UNAUTH";
	public static final String GET_TOKEN_TYPE = "GET_TOKEN";
	public static final String BYPASS_TYPE = "BYPASS";

	public static final String REQUEST_AUTHTOKEN_BEARER = "Bearer ";

	public static final int UNAUTH_SIGN_TIMESTAMP_ADD = 30000;
	public static final String REG_URL_END = "/$";

	public static final String GET_TOKEN = "get_token";
	public static final List<String> URL_GET_TOKEN_LIST = Arrays.asList(GET_TOKEN);
	

	
	// url중 proxy 포함시 ByPass 체크
	public static final String BYPASS_KEY = "proxy";

	public static final String CORS_PREFLIGHT_MAXAGE = "3600";
	public static final String CORS_SUPPORT_CREDENTIALS = "true";

	// default sesstionTime
	public static final String DEFAULT_SESSION_TOKEN_EXPIRE_TIME = "86500";

}
