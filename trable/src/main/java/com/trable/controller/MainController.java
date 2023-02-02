package com.trable.controller;

import java.io.File;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trable.dto.MemberFormDto;
import com.trable.entity.Member;
import com.trable.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	//MAINPAGE
	@GetMapping(value = "/")
	public String main() {
		return "main";
	}
	
	// OPEN LOGINPAGE
	@GetMapping(value = "/new")
	public String login(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "/member/loginpage";
	}
	
	// CLICK SIGNUP
	@PostMapping(value = "/new")
	public String signup(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model, 
			@RequestParam("user_img") File file) {
		
		if(bindingResult.hasErrors()) {
			return "/member/loginpage";
		}
		
		System.out.println(file.exists());
		
		if(file.exists()) {
			try {
				Member member = Member.createMember(memberFormDto, passwordEncoder);
				memberService.saveMember(member,file);
			} catch (IllegalStateException e) {
				model.addAttribute("errorMessage", e.getMessage());
				return "/member/loginpage";
			}
		}
		return "/travel/searchpage";
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
	@GetMapping(value = "/setting")
	public String settingpage() {
		return "/user/usersettingpage";
	}
}

