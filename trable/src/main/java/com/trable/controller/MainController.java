package com.trable.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trable.dto.MemberFormDto;
import com.trable.dto.PostFormDto;
import com.trable.dto.PostSearchDto;
import com.trable.entity.Member;
import com.trable.entity.Post;
import com.trable.entity.PostImg;
import com.trable.service.MemberService;
import com.trable.service.PostImgService;
import com.trable.service.PostService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MainController {

	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final PostService postservice;
	private final PostImgService postImgService;
	
	@Value("${ImgLocation}")
	private String imgLocation;
	
	// MAINPAGE
	@GetMapping(value = "/")
	public String main() {
		return "main";
	}
	
	// OPEN WRITING PAGE
	@GetMapping(value = "/write")
	public String write(Model model) {

		model.addAttribute("postFormDto", new PostFormDto());
		return "/travel/writingpage";
	}
	
	// WRITING POST
	@PostMapping(value = "/write")
	public String writenew(@Valid PostFormDto postFormDto, BindingResult bindingResult, Model model,
			@RequestParam("PostImgFile") List<MultipartFile> postImgFileList,
			@RequestParam("MainImgFile") MultipartFile postMainImg) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if(bindingResult.hasErrors()) {
			return "/travel/writingpage";
		}
		if(postImgFileList.get(0).isEmpty() && postFormDto.getPostname() == null) {
			model.addAttribute("errorMessage", "게시글에 최소 1장의 사진을 업로드 해주세요.");
		}
		// INPUT MAIN POSTIMG
		try {
			postservice.savePost(postFormDto, postImgFileList, postMainImg, email);
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "게시물 업로드 중 에러가 발생했습니다!");
			return "/travel/writingpage";
		} 
		return "redirect:/";
	}
	// OPEN DTL POSTPAGE 
	@GetMapping(value = {"/view", "/view/{id}"})
	public String dtlpage(@PathVariable("id") Long postid, Model model) {
		
		String id = SecurityContextHolder.getContext().getAuthentication().getName(); 
//		UserDetails user = memberService.loadUserByUsername(id);
//		Member member = memberService.findMember(user.getUsername());	
		try {
			Post post = postservice.getPostbyid(postid);
			Member member = memberService.findMemberbyId(post.getMember().getId());
			List<PostImg> postimgs = postImgService.getPostimg(postid);
			
			model.addAttribute("id", id);
			model.addAttribute("member", member);
			model.addAttribute("post", post);
			model.addAttribute("postimgs",postimgs);			
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "게시물 / 사용자를 불러올 수 없습니다.");
			return "/travel/searchpage";
		}
		return "/travel/dtlpage";
	}
	
	// OPEN UPDATEPAGE
	@GetMapping(value = "/update/{id}")
	public String updatepage(@PathVariable("id") Long postid, Model model) {
		
		try {
			PostFormDto postFormDto = postservice.getPostDto(postid);
			List<PostImg> postimgs = postImgService.getPostimg(postid);
			model.addAttribute("postFormDto",postFormDto);
			model.addAttribute("postimgs",postimgs);
			model.addAttribute("id",postid);
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "존재하지 않는 게시물입니다.");
			return "/view/{id}";
		}
		return "/travel/updatepage";
	}
	
	// UPDATE POST
	@PostMapping(value = "/update/{id}")
	public String updatepost(@PathVariable("id") Long postid, Model model, @Valid PostFormDto postFormDto,
			BindingResult bindingResult, @RequestParam("PostImgFile") List<MultipartFile> postImgFileList,
			@RequestParam("MainImgFile") MultipartFile postMainImg) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "데이터를 정상적으로 가져오지 못했습니다.");
		}
		
		if(postImgFileList.get(0).isEmpty() && postFormDto.getPostname() == null) {
			model.addAttribute("errorMessage", "게시글에 최소 1장의 사진을 업로드 해주세요.");
		}
		
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			postservice.updatePost(postFormDto, postImgFileList, postMainImg, email, postid);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "데이터 수정 중 오류가 발생했습니다.");
		}
		return "redirect:/";
	}
	
	
	// OPEN USERPAGE
	@GetMapping(value = "/user")
	public String userpage(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());	
		List<Post> memberpost = postservice.getUserPost(member);
		int heart = 0;
		
		for(int i =0; i<memberpost.size(); i++) {
			heart += memberpost.get(i).getHeart();
		}
		
		model.addAttribute("heart", heart);
		model.addAttribute("member", member);
		model.addAttribute("posts", memberpost);
		return "/user/userpage";
	}
	
	// SEARCH PAGE
	@GetMapping(value = "/find")
	public String searchpage(PostSearchDto postSearchDto, Model model) {
		
		List<Post> post = postservice.getPostPage();
		
		model.addAttribute("posts",post);
		
		model.addAttribute("imgLocation",imgLocation);
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

