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
	
	// MAINPAGE
	@GetMapping(value = "/")
	public String main() {
		return "main";
	}
	
	// OPEN WRITING PAGE
	@GetMapping(value = "/write")
	public String write(Model model) {
		model.addAttribute("postFormDto", new PostFormDto());
		return "/user/writingpage";
	}
	
	// CLICK WRITING BUTTON
	@PostMapping(value = "/write")
	public String writenew(@Valid PostFormDto postFormDto, BindingResult bindingResult, Model model,
			@RequestParam("PostImgFile") List<MultipartFile> postImgFileList) {
		
		if(bindingResult.hasErrors()) {
			return "/write";
		}
		
		if(postImgFileList.get(0).isEmpty() && postFormDto.getPostname() == null) {
			model.addAttribute("errorMessage", "게시글에 최소 1장의 사진을 업로드 해주세요.");
		}
		
		// INPUT MAIN POSTIMG
		
		
		
		// INPUT POSTIMG LIST
		
		return "redirect:find";
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

