package com.notice.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
	
	
	@RequestMapping("/api/test")
	public List<String> index(){
		
		return Arrays.asList("test", "test1", "test2");
		
	}
	
	
	
}
