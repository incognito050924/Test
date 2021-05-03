package com.example.test.common.exception;

public class ETException extends Exception {

	public ETException() {
		super();
	}
	
	public ETException(final String msg) {
		super(msg);
	}

	public ETException(final Throwable t) {
		super(t);
	}

	public ETException(final String msg, final Throwable t) {
		super(msg, t);
	}
}
