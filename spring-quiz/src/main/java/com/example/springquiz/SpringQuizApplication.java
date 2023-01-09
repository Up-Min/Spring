package com.example.springquiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@SpringBootApplication
public class SpringQuizApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringQuizApplication.class, args);
	}

	
	@GetMapping (value = "/")
	public String index() {
		return "새로운 과제 페이지";
	}
	
	@GetMapping (value = "/student1")
	public StudentDto student1() {
		StudentDto sd = new StudentDto();
		
		sd.setName("yuna");
		sd.setAge(20);
		sd.setJavaGrade("A+");
		sd.setOracleGrade("C");
		return sd;
	}
	
	@GetMapping (value = "/student2")
	public StudentDto student2() {
		StudentDto sd = new StudentDto();
		
		sd.setName("jimin");
		sd.setAge(21);
		sd.setJavaGrade("B+");
		sd.setOracleGrade("F");
		return sd;
	}
	
	
}
