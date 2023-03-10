package com.self.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.self.dto.MemberFormDto;
import com.self.entity.Member;
import com.self.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
		private final MemberService memberService;
		private final PasswordEncoder passwordEncoder;
	
	
		@GetMapping (value = "/signup")
		public String memberForm(Model model) {
			model.addAttribute("memberFormDto", new MemberFormDto());
			return "member/memberSignUpForm";
		}
		
		@PostMapping (value = "/signup")
		public String memberSignup(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
			
			if(bindingResult.hasErrors()) {
				return "member/memberSignUpForm";
			}
			
			try {
				Member member = Member.createMember(memberFormDto, passwordEncoder);
				memberService.saveMember(member);
			} catch (IllegalStateException e) {
				model.addAttribute("errorMessage", e.getMessage());
				return "member/memberSignUpForm";
			}
			return "redirect:/";
		}
	
		// LOGIN PAGE
		@GetMapping (value = "/login")
		public String loginForm() {
			return "member/memberLoginForm";
		}
		
		// LOGIN ERROR PAGE
		@GetMapping (value = "/login/error")
		public String loginError(Model model) {
			model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인하세요.");	
			return "member/memberLoginForm";
		}
		
	
}
