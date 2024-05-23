package com.example.product.exception;

import com.example.product.Version;

public class DataNotFoundException extends Exception {

	private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataNotFoundException(String message) {
		super(message);
	}

	public DataNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
