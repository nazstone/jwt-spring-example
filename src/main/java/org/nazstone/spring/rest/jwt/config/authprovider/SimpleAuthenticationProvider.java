package org.nazstone.spring.rest.jwt.config.authprovider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Simple provider d'authent
 * @author mlambert
 *
 */
public class SimpleAuthenticationProvider implements AuthenticationProvider {

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (authentication.getName() == null || authentication.getCredentials() == null
				|| !authentication.getName().equals(authentication.getCredentials().toString())) {
			throw new BadCredentialsException("Wrong user/pass");
		}
		return new UsernamePasswordAuthenticationToken(authentication.getName(), "");
	}

}
