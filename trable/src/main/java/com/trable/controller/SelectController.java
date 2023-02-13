package com.trable.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trable.entity.BlockMembers;
import com.trable.entity.BlockTags;
import com.trable.entity.Member;
import com.trable.entity.Post;
import com.trable.repository.PostRepository;
import com.trable.service.BlockService;
import com.trable.service.MemberService;
import com.trable.service.PostService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/Sselects")
@Controller
@RequiredArgsConstructor
public class SelectController {

	private final PostService postservice;
	private final PostRepository postRepository;
	private final BlockService blockService;
	private final MemberService memberService;
	
	@GetMapping(value = "/bylike")
	public String bylike(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());
		
		List<Post> post = postRepository.findBylike();
		List<BlockTags> Taglist = blockService.getblktag(member.getId());
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
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
	@GetMapping(value = "/byctime")
	public String byctime(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());
		
		List<Post> post = postRepository.findByctime();
		List<BlockTags> Taglist = blockService.getblktag(member.getId());
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
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
	@GetMapping(value = "/byrctime")
	public String byrctime(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());
		
		List<Post> post = postRepository.findByrctime();
		List<BlockTags> Taglist = blockService.getblktag(member.getId());
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
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
	@GetMapping(value = "/byutime")
	public String byutime(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());	
		
		List<Post> post = postRepository.findByutime();
		List<BlockTags> Taglist = blockService.getblktag(member.getId());
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
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
	@GetMapping(value = "/byrutime")
	public String byrutime(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());	
		
		List<Post> post = postRepository.findByrutime();
		List<BlockTags> Taglist = blockService.getblktag(member.getId());
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
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
