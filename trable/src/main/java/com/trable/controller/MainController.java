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

	@GetMapping(value = "/write")
	public String write() {
		return "/user/writingpage";
	}
	
	@GetMapping(value = "/view")
	public String dtlpage() {
		return "/travel/dtlpage";
	}
	
	@GetMapping(value = "/user")
	public String userpage() {
		return "/user/userpage";
	}
	
	@GetMapping(value = "/find")
	public String searchpage() {
		return "/travel/searchpage";
	}
	@GetMapping(value = "/like")
	public String likepage() {
		return "/travel/likepage";
	}
	
}

