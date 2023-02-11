package com.trable.controller;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

		// CLICK SIGNUP
		@PostMapping(value = "/new")
		public String signup(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model, @RequestParam("user_img") MultipartFile file) {
			if(bindingResult.hasErrors()) {
				return "member/signuppage";
			}
				try {
					// SAVE HALF (pureinfo of member)
					Member member = Member.createMember(memberFormDto, passwordEncoder); //
					Member member1 = memberService.saveMember(member);
					
					// SAVE OTHER HALF (IMG of member)
					userImgService.savememberImg(member1, file);

				} catch (Exception e) {
					model.addAttribute("errorMessage", e.getMessage());
					return "member/signuppage";
				}
			return "main";

		}
		
		// LOGIN ERROR
		@GetMapping(value = "/login/error")
		public String loginError(Model model){
			model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요.");
			return "member/loginpage";
		}
		
		// CHANGE PWD
		@GetMapping(value = "/changepw/{id}/{pw}")
		public String changepwd(@PathVariable("id") Long memberid, @PathVariable("pw") String pw) {
			System.out.println(memberid);
			System.out.println(pw);
			memberService.updateMemberpwd(memberid, pw, passwordEncoder);
			return "main";
		}
		
		// SETTING PAGE
		@GetMapping(value = "/setting/{id}")
		public String settingpage(@PathVariable("id") Long memberid, Model model) {
			String id = SecurityContextHolder.getContext().getAuthentication().getName();
			UserDetails user = memberService.loadUserByUsername(id);
			Member member = memberService.findMember(user.getUsername());	
			model.addAttribute("member",member);
			return "/user/usersettingpage";
		}
		
}
