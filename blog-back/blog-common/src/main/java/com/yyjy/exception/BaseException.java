package com.yyjy.exception;

public class BaseException extends RuntimeException{
	public BaseException() {
	}

	public BaseException(String message) {
		super(message);
	}
}
