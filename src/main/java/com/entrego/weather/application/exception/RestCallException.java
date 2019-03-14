package com.entrego.weather.application.exception;

public class RestCallException extends RuntimeException {

	private static final long serialVersionUID = 8681252703061858656L;

	public RestCallException(final String message) {
		super(message);
	}
	public RestCallException(final String message, final Throwable cause) {
		super(message, cause);
	}

}

