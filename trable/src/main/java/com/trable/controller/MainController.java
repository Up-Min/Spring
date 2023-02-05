package com.trable.controller;

import java.io.File;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trable.dto.MemberFormDto;
import com.trable.dto.PostFormDto;
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
	

	@GetMapping(value = "/write")
	public String write(Model model) {
		model.addAttribute("postFormDto", new PostFormDto());
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

