package com.myshop.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.myshop.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderservice;
	
	@PostMapping (value = "/order")
	public ResponseEntity order() {
		// 비동기
		
		
		
	}
}
