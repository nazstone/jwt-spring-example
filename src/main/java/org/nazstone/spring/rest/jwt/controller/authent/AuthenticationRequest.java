package org.nazstone.spring.rest.jwt.controller.authent;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
	/**uid*/
	private static final long serialVersionUID = -2311458791991135574L;
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
