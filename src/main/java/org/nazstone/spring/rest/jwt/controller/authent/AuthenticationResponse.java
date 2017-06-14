package org.nazstone.spring.rest.jwt.controller.authent;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
	/** uid */
	private static final long serialVersionUID = -930893707704892685L;
	private String token;
	private String refreshToken;

	public AuthenticationResponse(String responseToken, String refreshToken) {
		this.token = responseToken;
		this.refreshToken = refreshToken;
	}

	public String getToken() {
		return token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
