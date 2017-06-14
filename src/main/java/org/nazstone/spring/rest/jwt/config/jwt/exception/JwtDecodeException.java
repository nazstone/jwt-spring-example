package org.nazstone.spring.rest.jwt.config.jwt.exception;

public class JwtDecodeException extends RuntimeException {
	/** uid */
	private static final long serialVersionUID = -1914060222543318971L;

	public JwtDecodeException(String message, Throwable e) {
		super(message, e);
	}
}
