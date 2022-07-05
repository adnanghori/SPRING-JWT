package com.springboot.jwt.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.jwt.helper.JWTUTIL;
import com.springboot.jwt.model.JWTRequest;
import com.springboot.jwt.model.JWTResponse;
import com.springboot.jwt.service.UserDetailService;

@RestController
public class JWTController {
	@Autowired
	private UserDetailService userDetailService;
	@Autowired
	private JWTUTIL jwtutil;
	@Autowired
	private AuthenticationManager authenticationManager;
	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JWTRequest jwtRequest){
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		} catch (UsernameNotFoundException exception) {
			// TODO: handle exception
			throw new UsernameNotFoundException("BAD CREDENTIALS");
		} 
		UserDetails userDetails = this.userDetailService.loadUserByUsername(jwtRequest.getUsername());
		var generateToken = this.jwtutil.generateToken(userDetails);
		
		//we have to return  data in the form of json 
		// thus response entity will convert this token object into json and then json object will be returned
			return ResponseEntity.ok(new JWTResponse(generateToken));
			
	}
}
