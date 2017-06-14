package org.nazstone.spring.rest.jwt.config.jwt;

import java.time.Instant;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.nazstone.spring.rest.jwt.config.jwt.exception.JwtDecodeException;
import org.nazstone.spring.rest.jwt.config.jwt.exception.JwtExpirationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utilitaire pour jwt encodage, decodage et extracteur
 * 
 * @author mlambert
 *
 */
@Component
public class JwtUtil {
	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String HEADER_AUTHORIZATION_BEARER = "Bearer ";

	@Value("${jwt.key:clef}")
	private String key;
	@Value("${jwt.exp:3600}")
	private Integer exp;
	@Value("${jwt.exp.refresh:3600}")
	private Integer expRefresh;

	@Autowired
	private ObjectMapper mapper;

	public Token decode(String bearer) {
		Token token = null;
		try {
			MacSigner hmac = new MacSigner(key);
			Jwt jwt = JwtHelper.decodeAndVerify(bearer, hmac);
			// dont le temps d'expiration
			token = mapper.readValue(jwt.getClaims(), Token.class);
		} catch (Exception e) {
			throw new JwtDecodeException("erreur lors du decodage", e);
		}

		if (token.getExp() <= 0 || token.getExp() >= Instant.now().getEpochSecond()) {
			return token;
		}
		throw new JwtExpirationException();
	}

	public Jwt encode(Token token, boolean refresh) {
		try {
			if (refresh) {
				token.setExp(Instant.now().getEpochSecond() + expRefresh);
			} else {
				token.setExp(Instant.now().getEpochSecond() + exp);
			}
			MacSigner hmac = new MacSigner(key);
			Jwt jwt = JwtHelper.encode(mapper.writeValueAsString(token), hmac);
			return jwt;
		} catch (Exception e) {
			throw new RuntimeException("erreur lors de l'encodage");
		}
	}

	public static String extractBearer(ServletRequest request) {
		if (request instanceof HttpServletRequest) {
			// récupération des authorisations
			String authorization = ((HttpServletRequest) request).getHeader(HEADER_AUTHORIZATION);
			if (authorization != null && authorization.indexOf(HEADER_AUTHORIZATION_BEARER) >= 0) {
				// récupération des bearers
				String bearer = authorization.substring(
						authorization.indexOf(HEADER_AUTHORIZATION_BEARER) + HEADER_AUTHORIZATION_BEARER.length());
				return bearer.trim();
			}
		}
		return null;
	}
}
