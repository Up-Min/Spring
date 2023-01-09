package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// annotation 추가 -> restAPI를 가져오게 하기 위한 restcontroller
// @Controller + @responseBody -> 컨트롤러이자, 응답을 시키는 아이! -> springDemoApplication이 그러한 속성을 가질 수 있게 해준다.
@RestController

@SpringBootApplication
public class SpringDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoApplication.class, args);
	}
	
	@GetMapping(value = "/") //기본 사이트를 들어오면 hello world! 를 응답시킨다.
	public String HelloWorld() {
		return "Hello World!";
	}
	
	
	
	
	
}
