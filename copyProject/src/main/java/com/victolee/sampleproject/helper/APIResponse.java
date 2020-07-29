package com.victolee.sampleproject.helper;

public class APIResponse {
	private String resultCode;
	private String resultMessage;
	private Object result;
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}

	public String getResultString() {
		return "resultCode=" + resultCode + ", resultMessage=" + resultMessage;
	}
}
