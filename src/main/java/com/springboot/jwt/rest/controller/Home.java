package com.springboot.jwt.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class Home {
	@GetMapping("/home")
	public String home() {
		return "q";
	}
}
	