package org.nazstone.spring.rest.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patate")
public class PatateController {
	@GetMapping
	public ResponseEntity<String> tada() {
		return ResponseEntity.ok().body("secured patate default");
	}

	@GetMapping("tada")
	public ResponseEntity<String> tada2() {
		return ResponseEntity.ok().body("secured patate tada");
	}
}
