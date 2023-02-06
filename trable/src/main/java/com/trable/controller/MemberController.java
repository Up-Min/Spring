package com.trable.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trable.dto.MemberFormDto;
import com.trable.entity.Member;
import com.trable.service.MemberService;
import com.trable.service.UserImgService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final UserImgService userImgService;
	private final PasswordEncoder passwordEncoder;
	
		// OPEN SIGNUPPAGE
		@GetMapping(value = "/new")
		public String signup(Model model) {
			model.addAttribute("memberFormDto", new MemberFormDto());
			return "member/signuppage";
		}
			
		// OPEN LOGINPAGE
		@GetMapping(value = "/login")
		public String login() {
			return "member/loginpage";
		}
		
//		@PostMapping(value = "/login")
//		public String loginMember1() {
//			return "/main";
//		}

		// CLICK SIGNUP
		@PostMapping(value = "/new")
		public String signup(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model, @RequestParam("user_img") MultipartFile file) {
			if(bindingResult.hasErrors()) {
				return "member/signuppage";
			}
				try {
					Member member = Member.createMember(memberFormDto, passwordEncoder);
					Member member1 = memberService.saveMember(member);
					userImgService.savememberImg(member1, file);
				} catch (Exception e) {
					model.addAttribute("errorMessage", e.getMessage());
					return "member/signuppage";
				}
			return "/";

		}
		
		// LOGIN ERROR
		@GetMapping(value = "/login/error")
		public String loginError(Model model){
			model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요.");
			return "member/loginpage";
		}
		
		
}
