package com.trable.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trable.entity.Post;
import com.trable.repository.PostRepository;
import com.trable.service.PostService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/selects")
@Controller
@RequiredArgsConstructor
public class SelectController2 {

	private final PostService postservice;
	private final PostRepository postRepository;
	
	@GetMapping(value = "/bylike")
	public String bylike(Model model) {
		List<Post> post = postRepository.findBylike();
		model.addAttribute("posts",post);
		return "/travel/likepage";
	}
	@GetMapping(value = "/byctime")
	public String byctime(Model model) {
		List<Post> post = postRepository.findByctime();
		model.addAttribute("posts",post);
		return "/travel/likepage";		
	}
	@GetMapping(value = "/byrctime")
	public String byrctime(Model model) {
		List<Post> post = postRepository.findByrctime();
		model.addAttribute("posts",post);
		return "/travel/likepage";		
	}
	@GetMapping(value = "/byutime")
	public String byutime(Model model) {
		List<Post> post = postRepository.findByutime();
		model.addAttribute("posts",post);
		return "/travel/likepage";		
	}
	@GetMapping(value = "/byrutime")
	public String byrutime(Model model) {
		List<Post> post = postRepository.findByrutime();
		model.addAttribute("posts",post);
		return "/travel/likepage";		
	}
	
	
}
