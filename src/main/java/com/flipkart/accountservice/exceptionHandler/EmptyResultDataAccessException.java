package com.flipkart.accountservice.exceptionHandler;

public class EmptyResultDataAccessException extends RuntimeException {
	
	private String msg;
	
	public EmptyResultDataAccessException(String msg) {
		super(msg);
		this.msg = msg;
	}

}
