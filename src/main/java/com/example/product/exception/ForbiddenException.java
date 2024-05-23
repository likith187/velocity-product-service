package com.example.product.exception;

import com.example.product.Version;

public class ForbiddenException extends Exception {
	
	private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForbiddenException(String message) {
		super(message);
	}

	public ForbiddenException(Throwable cause) {
		super(cause);
	}
}
