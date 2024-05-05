package com.nick.ms.error;

public class GeneralErrorResponse {
	
	String message;

	public GeneralErrorResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
