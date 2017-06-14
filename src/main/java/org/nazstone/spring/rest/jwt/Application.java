package org.nazstone.spring.rest.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//https://github.com/SNCF-SIV/spring-security-rest-jwt-ldap/tree/master/src/main/java/com/sncf/siv/poc/security
@SpringBootApplication
@ComponentScan(basePackages = { "org.nazstone.spring" })
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
