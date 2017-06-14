package org.nazstone.spring.rest.jwt.config;

import javax.servlet.Filter;

import org.nazstone.spring.rest.jwt.config.authprovider.SimpleAuthenticationProvider;
import org.nazstone.spring.rest.jwt.config.filter.ClientCredentialsTokenEndpointFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint()).and()
				.addFilterBefore(clientCredentialsTokenEndpointFilter(), BasicAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/superadmin/**").hasRole("ADMIN").antMatchers("/patate/**").hasRole("PATATE")
				.antMatchers("/auth/refresh").hasRole("REFRESH")
				.antMatchers("/auth/**").permitAll().antMatchers("/**").permitAll().anyRequest().permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new SimpleAuthenticationProvider());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public Filter clientCredentialsTokenEndpointFilter() {
		return new ClientCredentialsTokenEndpointFilter();
	}

}
