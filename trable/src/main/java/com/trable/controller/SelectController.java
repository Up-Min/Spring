package com.trable.controller;

import java.util.ArrayList;
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
import com.trable.service.TagService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/selects")
@Controller
@RequiredArgsConstructor
public class SelectController {

	private final PostService postservice;
	private final PostRepository postRepository;
	private final BlockService blockService;
	private final MemberService memberService;
	private final TagService tagService;
	
	@GetMapping(value = "/bylike")
	public String bylike(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());
		
		List<BlockTags> Taglist = blockService.getblktag(member.getId());
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
		List<String> finaltag = tagService.returntagsleast2times();
		List<Post> post = postservice.getPostsUsingFinalTagbylike(finaltag);
		
		for(int i=0; i<post.size(); i++) {
			for(int j=0; j<Memlist.size(); j++) {
				if(post.get(i).getMember().getEmail().equals(Memlist.get(j).getBlockmembername())) {
					post.remove(post.get(i));
				}
			}
		}
		model.addAttribute("posts",post);
		return "/travel/likepage";
	}
	@GetMapping(value = "/byctime")
	public String byctime(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());
		
		List<BlockTags> Taglist = blockService.getblktag(member.getId());
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
		List<String> finaltag = tagService.returntagsleast2times();
		List<Post> post = postservice.getPostsUsingFinalTagbyctime(finaltag);
		
		for(int i=0; i<post.size(); i++) {
			for(int j=0; j<Memlist.size(); j++) {
				if(post.get(i).getMember().getEmail().equals(Memlist.get(j).getBlockmembername())) {
					post.remove(post.get(i));
				}
			}
		}
		model.addAttribute("posts",post);
		return "/travel/likepage";		
	}
	@GetMapping(value = "/byrctime")
	public String byrctime(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());
		
		List<BlockTags> Taglist = blockService.getblktag(member.getId());
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
		List<String> finaltag = tagService.returntagsleast2times();
		List<Post> post = postservice.getPostsUsingFinalTagbyrctime(finaltag);
		
		for(int i=0; i<post.size(); i++) {
			for(int j=0; j<Memlist.size(); j++) {
				if(post.get(i).getMember().getEmail().equals(Memlist.get(j).getBlockmembername())) {
					post.remove(post.get(i));
				}
			}
		}
		model.addAttribute("posts",post);
		return "/travel/likepage";		
	}
	@GetMapping(value = "/byutime")
	public String byutime(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());	
		
		List<BlockTags> Taglist = blockService.getblktag(member.getId());
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
		List<String> finaltag = tagService.returntagsleast2times();
		List<Post> post = postservice.getPostsUsingFinalTagbyutime(finaltag);
		
		for(int i=0; i<post.size(); i++) {
			for(int j=0; j<Memlist.size(); j++) {
				if(post.get(i).getMember().getEmail().equals(Memlist.get(j).getBlockmembername())) {
					post.remove(post.get(i));
				}
			}
		}
		model.addAttribute("posts",post);
		return "/travel/likepage";		
	}
	@GetMapping(value = "/byrutime")
	public String byrutime(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());	
		
		List<BlockTags> Taglist = blockService.getblktag(member.getId());
		List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
		
		List<String> finaltag = tagService.returntagsleast2times();
		List<Post> post = postservice.getPostsUsingFinalTagbyrutime(finaltag);
		
		for(int i=0; i<post.size(); i++) {
			for(int j=0; j<Memlist.size(); j++) {
				if(post.get(i).getMember().getEmail().equals(Memlist.get(j).getBlockmembername())) {
					post.remove(post.get(i));
				}
			}
		}
		model.addAttribute("posts",post);
		return "/travel/likepage";		
	}
	
	
}
