package com.victolee.sampleproject.utils;

public enum RequiredParameter {
	REQUIRED_TRANSACTION_ID("TransactionId"),
	REQUIRED_TIMESTAMP("TimeStamp"),
	REQUIRED_ALL("All");
	
	private String requiredParameter;
	
	private RequiredParameter(String requiredParameter) {
		this.requiredParameter = requiredParameter;
	}
	
	public String getParameter() {
		return requiredParameter;
	}
}

