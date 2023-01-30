package com.trable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	
	@GetMapping(value = "/")
	public String main() {
		
		return "main";

	}
	
	@GetMapping(value = "/new")
	public String login() {
		
		return "/member/loginpage";
	}

}

