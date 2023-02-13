package com.trable.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

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

import com.trable.dto.PostFormDto;
import com.trable.entity.BlockMembers;
import com.trable.entity.BlockTags;
import com.trable.entity.Member;
import com.trable.entity.Post;
import com.trable.entity.PostImg;
import com.trable.entity.Tag;
import com.trable.repository.BlockMembersRepository;
import com.trable.repository.BlockTagsRepository;
import com.trable.repository.PostRepository;
import com.trable.service.BlockService;
import com.trable.service.MemberService;
import com.trable.service.PostImgService;
import com.trable.service.PostService;
import com.trable.service.TagService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MainController {

	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final PostService postservice;
	private final PostImgService postImgService;
	private final TagService tagService;
	private final PostRepository postRepository;
	private final BlockService blockService;
	
	@Value("${ImgLocation}")
	private String imgLocation;
	
	// MAINPAGE
	@GetMapping(value = "/")
	public String main(Model model, Post posttest) {
		List<Post> post = postservice.getPostShowPage(posttest.getShowPost());
		model.addAttribute("posts",post);
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
			@RequestParam("MainImgFile") MultipartFile postMainImg, 
			@RequestParam("Tag") String tag) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if(bindingResult.hasErrors()) {
			return "/travel/writingpage";
		}
		if(postImgFileList.get(0).isEmpty() && postFormDto.getPostname() == null) {
			model.addAttribute("errorMessage", "게시글에 최소 1장의 사진을 업로드 해주세요.");
		}
		if(tag == null) {
			model.addAttribute("errorMessage", "태그를 작성해주세요.");
		}
		// INPUT MAIN POSTIMG
		try {
			// CREATE & SAVE POST
			Long postid = postservice.savePost(postFormDto, postImgFileList, postMainImg, email);
			
			// GET POST BY POSTID
			Post post = postservice.getPostbyid(postid);
			
			// CREATE & SAVE TAG, POSTTAG
			tagService.saveTag(tag, post);
			
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
		
		String id = SecurityContextHolder.getContext().getAuthentication().getName(); //
//		UserDetails user = memberService.loadUserByUsername(id);
//		Member member = memberService.findMember(user.getUsername());	
		try {
			Post post = postservice.getPostbyid(postid);
			Member member = memberService.findMemberbyId(post.getMember().getId());
			List<PostImg> postimgs = postImgService.getPostimg(postid);
			List<Tag> tags = tagService.findbypostid(postid);
			
			model.addAttribute("id", id);
			model.addAttribute("member", member);
			model.addAttribute("post", post);
			model.addAttribute("postimgs",postimgs);		
			model.addAttribute("tags", tags);
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "게시물 / 사용자를 불러올 수 없습니다.");
			return "/travel/searchpage";
		}
		return "/travel/dtlpage";
	}
	
	// GET HEART
	@GetMapping(value = "/heart/{id}")
	public String updateheart(@PathVariable("id") Long postid, Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		try {
			Post post = postservice.getPostbyid(postid);
			Post post1 = postservice.updatePostHeart(post);
			Member member = memberService.findMemberbyId(post1.getMember().getId());
			
			
			List<PostImg> postimgs = postImgService.getPostimg(postid);
			List<Tag> tags = tagService.findbypostid(postid);
			
			model.addAttribute("id", id);
			model.addAttribute("member", member);
			model.addAttribute("post", post1);
			model.addAttribute("postimgs",postimgs);		
			model.addAttribute("tags", tags);
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
		
		// GET TOTAL MEMBER HEARTS, CALCULATE USER GRADE BY HEARTS
		for(int i =0; i<memberpost.size(); i++) {
			heart += memberpost.get(i).getHeart();
		}
		Member member1 = memberService.updateMembergrade(member.getId(), heart);
		
		// GET TAGS WHAT USER USED LEAST 2 TIMES.
		List<Tag> tag = tagService.getTagnamebycount(member.getId());
		List<String> tagnamelist = new ArrayList<>();
		for(int i = 0; i<tag.size(); i++ ) {
			 tagnamelist.add(tag.get(i).getTagname());
		}
		List<String> enoughtagname = new ArrayList<>();
		for(int i=0; i<tagnamelist.size(); i++) {
			int count = 0;
			for(int j=0; j<tagnamelist.size(); j++) {
				if(tagnamelist.get(i) == tagnamelist.get(j)) {
					count ++;					
				}
			}
			if(count > 1) {
				enoughtagname.add(tagnamelist.get(i));
			}
		}
		List<String> finaltag = new ArrayList<>();
		for(String test : enoughtagname) {
			if(!finaltag.contains(test)) {
				finaltag.add(test);				
			}
		}
		System.out.println("show tags used more than 2times : " + finaltag);
		

		// ADD ATTRIBUTE
		model.addAttribute("tags",finaltag);
		model.addAttribute("heart", heart);
		model.addAttribute("member", member1);
		model.addAttribute("posts", memberpost);
		return "/user/userpage";
	}
	
	// SEARCH PAGE
	@GetMapping(value = "/find")
	public String searchpage(Model model, Post posttest) {
		
		List<Post> post = postservice.getPostShowPage(posttest.getShowPost());
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());	
		
	
		
		
		// CHECK HAS BLOCK Member LIST
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
		model.addAttribute("imgLocation",imgLocation);
		return "/travel/searchpage";
	}
	
//	@RequestMapping(value = "/findpostbytagAJAX")
//	public List<Post> returnpost(@RequestBody String tag){
//		String json = null;
//		try {
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return postservice.getPostbyTag(tag);
//	}
	
	// RECOMMEND PAGE
	@GetMapping(value = "/like")
	public String likepage(Model model, Post post) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());	
		
		// GET TAGS WHAT USER USED LEAST 2 TIMES.
		List<Tag> tag = tagService.getTagnamebycount(member.getId());
		List<String> tagnamelist = new ArrayList<>();
		for(int i = 0; i<tag.size(); i++ ) {
			 tagnamelist.add(tag.get(i).getTagname());
		}
		List<String> enoughtagname = new ArrayList<>();
		for(int i=0; i<tagnamelist.size(); i++) {
			int count = 0;
			for(int j=0; j<tagnamelist.size(); j++) {
				if(tagnamelist.get(i) == tagnamelist.get(j)) {
					count ++;					
				}
			}
			if(count > 1) {
				enoughtagname.add(tagnamelist.get(i));
			}
		}
		List<String> finaltag = new ArrayList<>();
		for(String test : enoughtagname) {
			if(!finaltag.contains(test)) {
				finaltag.add(test);				
			}
		}
		
		// GET POSTS BY USE FINALTAG
		List<Post> tagpost = new ArrayList<>();
		for(String eachtag : finaltag) {
			List<Post> casepost = postservice.getPostbyTagname(eachtag);//각 태그별로 가져온 postlist
			System.out.println("casepost : " + casepost);
			for(int i=0; i<casepost.size(); i++) {
				if(!tagpost.contains(casepost.get(i))) {
					tagpost.add(casepost.get(i));
				}
			}
		}
		
		model.addAttribute("posts",tagpost);
		return "/travel/likepage";
	}

	// HIDE POST
	@GetMapping(value = "/hide/{id}")
	public String hidepost(@PathVariable("id") Long postid, Member member) {
		postservice.getPostbyid(postid).updatePostHide();
		postRepository.save(postservice.getPostbyid(postid));
		
		return "redirect:/";
	}
	// SHOW POST
	@GetMapping(value = "/show/{id}")
	public String showpost(@PathVariable("id") Long postid, Member member) {
		postservice.getPostbyid(postid).updatePostShow();
		postRepository.save(postservice.getPostbyid(postid));
		return "redirect:/";
	}
	
	// DELETE POST
	@GetMapping(value = "/delete/{id}")
	public String deletepost(@PathVariable("id") Long postid, Member member) {
		postservice.deletePost(postid);
		return "redirect:/";
	}
	
}

