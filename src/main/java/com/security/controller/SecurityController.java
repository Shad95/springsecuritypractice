package com.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class SecurityController {
	
	@GetMapping("/welcome")
	public String  hello() {
		
		String text = "hi,how are you";
		text += "this page is not allowed for unauthenticated users";
		return text;
	}

}
