package com.trable.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trable.entity.BlockMembers;
import com.trable.entity.BlockTags;
import com.trable.entity.Member;
import com.trable.entity.Post;
import com.trable.entity.Tag;
import com.trable.repository.PostRepository;
import com.trable.service.BlockService;
import com.trable.service.MemberService;
import com.trable.service.PostService;
import com.trable.service.TagService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/searchs")
@Controller
@RequiredArgsConstructor
public class SearchController {

	private final PostService postservice;
	private final MemberService memberService;
	private final BlockService blockService;
	private final TagService tagService;
	private final PostRepository postRepository;
	
	
	// SEARCH PAGE
	@GetMapping(value = {"/find","/find/{tags}"})
	public String searchpage(Model model, Post posttest,@PathVariable("tags") Optional<String> searchtag) {
		
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());	
		
		List<Post> post = new ArrayList<>();
		// CHECK BLOCK Member LIST
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		System.out.println("차단 멤버 : " + Memlist);
		
		if(searchtag.isEmpty()) {
		post = postRepository.findByMemberNotshow();
		}else {
		post = postRepository.findByTag2(searchtag);			
		}
				
		for(int i=0; i<post.size(); i++) {
			for(int j=0; j<Memlist.size(); j++) {
				if(post.get(i).getMember().getEmail().equals(Memlist.get(j).getBlockmembername())) {
					post.remove(post.get(i));
				}
			}
		}	
		model.addAttribute("posts",post);
		return "/travel/searchpage";
	}
	
	@GetMapping(value = {"/bylike","/bylike/{tags}"})
	public String bylike(Model model,@PathVariable("tags") Optional<String> searchtag) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());
		
		List<Post> post = new ArrayList<>();
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
		if(searchtag.isEmpty()) {
		post = postRepository.findBylike();
		}else {
		post = postRepository.findBylikeSearch(searchtag);			
		}
		
		for(int i=0; i<post.size(); i++) {
			for(int j=0; j<Memlist.size(); j++) {
				if(post.get(i).getMember().getEmail().equals(Memlist.get(j).getBlockmembername())) {
					post.remove(post.get(i));
				}
			}
		}		
		model.addAttribute("posts",post);
		return "/travel/searchpage";
	}
	
	@GetMapping(value = {"/byctime","/byctime/{tags}"})
	public String byctime(Model model,@PathVariable("tags") Optional<String> searchtag) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());

		List<Post> post = new ArrayList<>();
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
		if(searchtag.isEmpty()) {
		post = postRepository.findByctime();
		}else {
		post = postRepository.findByctimeSearch(searchtag);			
		}
		
		for(int i=0; i<post.size(); i++) {
			for(int j=0; j<Memlist.size(); j++) {
				if(post.get(i).getMember().getEmail().equals(Memlist.get(j).getBlockmembername())) {
					post.remove(post.get(i));
				}
			}
		}
		
		model.addAttribute("posts",post);
		return "/travel/searchpage";		
	}
	@GetMapping(value = {"/byrctime","/byrctime/{tags}"})
	public String byrctime(Model model,@PathVariable("tags") Optional<String> searchtag) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());

		List<Post> post = new ArrayList<>();
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
		if(searchtag.isEmpty()) {
		post = postRepository.findByrctime();
		}else {
		post = postRepository.findByrctimeSearch(searchtag);			
		}
		
		for(int i=0; i<post.size(); i++) {
			for(int j=0; j<Memlist.size(); j++) {
				if(post.get(i).getMember().getEmail().equals(Memlist.get(j).getBlockmembername())) {
					post.remove(post.get(i));
				}
			}
		}
		
		
		model.addAttribute("posts",post);
		return "/travel/searchpage";		
	}
	@GetMapping(value = {"/byutime","/byutime/{tags}"})
	public String byutime(Model model,@PathVariable("tags") Optional<String> searchtag) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());

		List<Post> post = new ArrayList<>();
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
		if(searchtag.isEmpty()) {
		post = postRepository.findByutime();
		}else {
		post = postRepository.findByutimeSearch(searchtag);			
		}
		
		for(int i=0; i<post.size(); i++) {
			for(int j=0; j<Memlist.size(); j++) {
				if(post.get(i).getMember().getEmail().equals(Memlist.get(j).getBlockmembername())) {
					post.remove(post.get(i));
				}
			}
		}
		
		model.addAttribute("posts",post);
		return "/travel/searchpage";		
	}
	@GetMapping(value = {"/byrutime","/byrutime/{tags}"})
	public String byrutime(Model model,@PathVariable("tags") Optional<String> searchtag) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());

		List<Post> post = new ArrayList<>();
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
		if(searchtag.isEmpty()) {
		post = postRepository.findByrutime();
		}else {
		post = postRepository.findByrutimeSearch(searchtag);			
		}
		
		for(int i=0; i<post.size(); i++) {
			for(int j=0; j<Memlist.size(); j++) {
				if(post.get(i).getMember().getEmail().equals(Memlist.get(j).getBlockmembername())) {
					post.remove(post.get(i));
				}
			}
		}
		
		model.addAttribute("posts",post);
		return "/travel/searchpage";		
	}
	
	
}
