package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.helper.JwtUtil;
import com.security.model.JwtRequest;
import com.security.model.JwtResponse;
import com.security.service.CustomUserDetailsService;

@RestController
public class JwtController {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception  {
		
		System.out.println(jwtRequest);
		
		try {
			this.authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		}
		catch(UsernameNotFoundException e) {
			
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		}
		catch(BadCredentialsException e)   {
			
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		
		}
	UserDetails userDetails =	this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
  String token =	this.jwtUtil.generateToken(userDetails);
  
  return ResponseEntity.ok(new JwtResponse(token));
	}
}
