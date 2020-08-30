package com.revature.exceptions;

public class SQLSecurityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6062892807894644299L;
	
	public SQLSecurityException() {
		super();
	}
	
	public SQLSecurityException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SQLSecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public SQLSecurityException(String message) {
		super(message);
	}

	public SQLSecurityException(Throwable cause) {
		super(cause);
	}
	
}
