package org.nazstone.spring.rest.jwt.config.jwt;

import java.util.List;

public class Token {
	/** issuer : application called */
	private String iss;
	/** expiration time */
	private Long exp;
	/** subject : user */
	private String sub;
	/** roles */
	private List<String> scopes;

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public Long getExp() {
		return exp;
	}

	public void setExp(Long exp) {
		this.exp = exp;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public List<String> getScopes() {
		return scopes;
	}

	public void setScopes(List<String> roles) {
		this.scopes = roles;
	}
}
