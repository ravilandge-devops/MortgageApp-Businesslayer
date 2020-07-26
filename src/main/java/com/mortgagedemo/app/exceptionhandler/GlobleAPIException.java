package com.mortgagedemo.app.exceptionhandler;

import org.springframework.http.HttpStatus;

public class GlobleAPIException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpStatus statusCode;

	public GlobleAPIException(String message, HttpStatus statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

}
