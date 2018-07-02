package com.lookfood.backend.services.exceptions;

public class ReviewClosedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ReviewClosedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReviewClosedException(String message) {
		super(message);
	}

}
