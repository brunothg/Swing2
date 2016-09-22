package com.github.brunothg.swing2.exception;

public class NotClosableException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotClosableException() {
		super();
	}

	public NotClosableException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotClosableException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotClosableException(String message) {
		super(message);
	}

	public NotClosableException(Throwable cause) {
		super(cause);
	}

}
