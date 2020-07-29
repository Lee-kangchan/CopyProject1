package com.victolee.sampleproject.utils;

public class RequestInfo implements ILogParameterInfo{
	
	private String url;						//	��û URL
	private String ip;						//	��û IP
	private String method;					//	��û HTTP METHOD
	private String transactionId;			//	��û Ʈ������ ���� ID 
	private String referer;					//	��û ���۷� (���� ��û URL) Referer: https://www.wehago.com/
	private String userAgent;				//	��û �ܸ� ���� ex) User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko
	private String timeStamp;				// 	��û �ð� longtime
	private String clientId;				//  ��û ����� ���̵�  	
	private String tokenType;				//	��ū Ÿ�� : unAuth(������), Auth(����)
	private String unAuthSign;				//	������ ���� signature
	private String authSign;				//	���� ���� wehago-sign
	private String authToken;				//	���� ��ū Authorization
	private String contextPath;				// 	context ���
	private String apiCode;					//	API �ڵ�
	
	public String getUrl() {
		return url;
	}
	public void setUri(String url) {
		this.url = url;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	@Override
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getUnAuthSign() {
		return unAuthSign;
	}
	public void setUnAuthSign(String unAuthSign) {
		this.unAuthSign = unAuthSign;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getAuthSign() {
		return authSign;
	}
	public void setAuthSign(String authSign) {
		this.authSign = authSign;
	}
	@Override
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	@Override
	public String getApiCode() {
		return apiCode;
	}
	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}
	
	@Override
	public String toString() {
		return "[transactionId : " + this.transactionId + "][Method : " + this.method + "][IP : " + this.ip + "][URL : " + this.url + "]"
				+ "[tokenType : " + this.tokenType + "][AuthSign : " + this.authSign + "][ContextPath : " + this.contextPath + "][UnAuthSign : "
				+ this.unAuthSign + "]";
	}

}
