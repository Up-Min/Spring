package com.trable.controller;

import java.util.List;

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
import com.trable.entity.BlockMembers;
import com.trable.entity.BlockTags;
import com.trable.entity.Member;
import com.trable.repository.BlockTagsRepository;
import com.trable.service.BlockService;
import com.trable.service.MemberService;
import com.trable.service.UserImgService;

import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;
import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final UserImgService userImgService;
	private final BlockService blockService;
	private final PasswordEncoder passwordEncoder;
	private final BlockTagsRepository blockTagsRepository;

	
		// OPEN SIGNUPPAGE
		@GetMapping(value = "/new")
		public String signup(Model model) {
			model.addAttribute("memberFormDto", new MemberFormDto());
			return "/member/signuppage";
		}
			
		// OPEN LOGINPAGE
		@GetMapping(value = "/login")
		public String login() {
			return "/member/loginpage";
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
			return "/member/loginpage";

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
			return "/members/logout";
		}
		
		// SETTING PAGE
		@GetMapping(value = "/setting/{id}")
		public String settingpage(@PathVariable("id") Long memberid, Model model) {
			String id = SecurityContextHolder.getContext().getAuthentication().getName();
			UserDetails user = memberService.loadUserByUsername(id);
			Member member = memberService.findMember(user.getUsername());	
			List<BlockTags> Taglist = blockService.getblktag(member.getId());
			List<BlockMembers> Memlist = blockService.getblkmem(member.getId());
			System.out.println(Memlist);
			
			if(Taglist.size() >= 1) {
				model.addAttribute("blocktags",Taglist);
			}
			if(Memlist.size() >= 1) {
				model.addAttribute("blockmems",Memlist);
			}
			
			model.addAttribute("member",member);
			return "/user/usersettingpage";
		}
		
		// BLOCK USER
		@PostMapping(value = "/userblock/{id}")
		public String userblock(@PathVariable("id") Long memberid, Model model,
				@RequestParam("blockuser") List<String> blockuser) {
			String id = SecurityContextHolder.getContext().getAuthentication().getName();
			Member member = memberService.findMemberbyId(memberid);
			for(String user : blockuser) {
				if(user == id) {
					model.addAttribute("errorMessage", "자기 자신은 차단할 수 없습니다!");
					return "/setting/{id}";
				}
				blockService.createBlockMember(member, user);
			}
			return "redirect:/";
		}
		
		// BLOCK TAG
		@PostMapping(value = "/tagblock/{id}")
		public String tagblock(@PathVariable("id") Long memberid, Model model,
				@RequestParam("blocktag") List<String> blocktag) {
			Member member = memberService.findMemberbyId(memberid);
			System.out.println("member : "+member);
			System.out.println("blocktag : "+blocktag);
			for(String tag : blocktag) {
				System.out.println("tag" + tag);
				blockService.createBlockTag(member, tag);
			}
			return "redirect:/";
		}
		
		// RESET BLOCK SETTING
		@GetMapping(value = "/resetset/{id}")
		public String resetset(@PathVariable("id") Long memberid) {
			blockService.deleteBlockMember(memberid);
			blockService.deleteBlockTag(memberid);
			return "redirect:/";
		}
		
		// DELETE USER
		@GetMapping(value = "/deleteuser/{id}")
		public String deleteuser(@PathVariable("id") Long memberid) {
			memberService.deletemember(memberid);
			return "redirect:/";
		}
		
}
