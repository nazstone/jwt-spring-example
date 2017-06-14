package org.nazstone.spring.rest.jwt.controller.authent;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nazstone.spring.rest.jwt.config.jwt.JwtUtil;
import org.nazstone.spring.rest.jwt.config.jwt.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthentificationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping
	public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		// récupération des login/pass
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();

		// authent
		Authentication authentication = this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// création du token
		Jwt jwt = createToken(authentication.getName());

		// création du refresh token
		Token refreshToken = new Token();
		refreshToken.setIss("refreshTokenAppName");
		refreshToken.setSub(authentication.getName());
		refreshToken.setScopes(Arrays.asList(new String[] { "REFRESH" }));

		Jwt jwtRefresh = jwtUtil.encode(refreshToken, true);

		Cookie cookie = new Cookie("jwt", new Long(new Date().getTime()).toString());
		cookie.setHttpOnly(true);
		cookie.setValue(jwt.getEncoded());
		httpServletResponse.addCookie(cookie);

		return ResponseEntity.ok(new AuthenticationResponse(jwt.getEncoded(), jwtRefresh.getEncoded()));
	}

	@GetMapping("/refresh")
	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse httpServletResponse) {
		String bearer = JwtUtil.extractBearer(request);

		Token refreshToken = jwtUtil.decode(bearer);
		Jwt jwt = createToken(refreshToken.getSub());

		Jwt jwtRefresh = jwtUtil.encode(refreshToken, true);

		Cookie cookie = new Cookie("jwt", new Long(new Date().getTime()).toString());
		cookie.setHttpOnly(true);
		cookie.setValue(jwt.getEncoded());
		httpServletResponse.addCookie(cookie);

		return ResponseEntity.ok(new AuthenticationResponse(jwt.getEncoded(), jwtRefresh.getEncoded()));
	}

	private Jwt createToken(String name) {
		Token token = new Token();
		token.setIss("tokenAppName");
		token.setSub(name);
		if (name.equals("admin")) {
			token.setScopes(Arrays.asList(new String[] { "ADMIN", "PATATE" }));
		} else if (name.equals("patate")) {
			token.setScopes(Arrays.asList(new String[] { "PATATE" }));
		} else {
			token.setScopes(Arrays.asList(new String[] { "NONE" }));
		}

		Jwt jwt = jwtUtil.encode(token, false);
		return jwt;
	}

}
