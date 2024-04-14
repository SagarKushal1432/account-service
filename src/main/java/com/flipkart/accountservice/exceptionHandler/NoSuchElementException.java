package com.flipkart.accountservice.exceptionHandler;

import lombok.Data;

@Data
public class NoSuchElementException extends RuntimeException {

	private String msg;

	public NoSuchElementException(String msg) {
		super(msg);
		this.msg = msg;
	}
}
