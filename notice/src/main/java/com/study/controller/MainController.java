package com.study.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	
	@RequestMapping("/api/")
	public List<String> maintest(){
		return Arrays.asList("test", "test");
	}
	
	
}
