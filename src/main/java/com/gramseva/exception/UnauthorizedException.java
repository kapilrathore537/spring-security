package com.gramseva.exception;


import com.gramseva.utils.Constants;

public class UnauthorizedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnauthorizedException() {
		super(Constants.UNAUTHORIZED_ACCESS);
		// TODO Auto-generated constructor stub
	}

	public UnauthorizedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
