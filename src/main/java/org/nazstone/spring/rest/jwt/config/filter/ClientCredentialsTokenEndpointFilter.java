package org.nazstone.spring.rest.jwt.config.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.nazstone.spring.rest.jwt.config.jwt.JwtUtil;
import org.nazstone.spring.rest.jwt.config.jwt.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filtre toutes les requetes entrantes pour vérifier le bearer
 * 
 * @author mlambert
 *
 */
public class ClientCredentialsTokenEndpointFilter extends GenericFilterBean {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String bearer = JwtUtil.extractBearer(request);

		if (bearer != null) {
			// récupération du payload
			Token token = jwtUtil.decode(bearer.trim());

			// création des roles
			List<SimpleGrantedAuthority> authorities = new ArrayList<>();
			for (String role : token.getScopes()) {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			}

			// set dans le security context
			SecurityContextHolder.getContext()
					.setAuthentication(new AnonymousAuthenticationToken(token.getSub(), token.getIss(), authorities));
		}

		chain.doFilter(request, response);
	}

}
