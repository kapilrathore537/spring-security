package com.gramseva.exception;


import com.gramseva.utils.Constants;

public class BadRequestException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}

}
