package com.example.springself;

import java.lang.reflect.Member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.introspect.MemberKey;

@Controller
public class studentDTO {
	String name = null;
	int age = 0;
	String javaGrade = null;
	String oracleGrade = null;
	
	@GetMapping ("/student1")
	public String student1(Model model) {
		
		
		String name = "yuna";
		int age = 20;
		String javaGrade = "yuna";
		String oracleGrade = "yuna";
		
		
		model.addAttribute("name",name);
		model.addAttribute("age",age);
		model.addAttribute("javaGrade",javaGrade);
		model.addAttribute("oracleGrade",oracleGrade);
		
		
		return "student1";
	}
	@GetMapping ("/student2")
	public String student2() {
		name = "jimin";
		age = 21;
		javaGrade = "B+";
		oracleGrade = "F";
		return "student2";
	}
}


