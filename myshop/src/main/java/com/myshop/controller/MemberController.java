package com.myshop.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myshop.dto.MemberFormDto;
import com.myshop.entity.Member;
import com.myshop.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor //얘를 넣으면 final 키워드가 있는 의존성 주입을 할 수 있는것을 기억하라
public class MemberController {
	// 결국 test에서 했던거랑 유사하게 간다.
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	// get, post 방식에 따라 받는거를 나눠주기 위해 동일한 이름의 필드를 두개 선언
	
	//회원가입 화면을 보여줌
	@GetMapping(value = "/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		// MemberFormDto 객체를 넘겨준다.
		return "member/memberForm";
		//get 방식의 new로 들어오면 memberForm 페이지를 보여줄텐데,
		// model을 이용해서 MemberFormDto도 같이 보여줄거다.
	}
	
	// 회원가입 버튼을 눌렀을 때 보여줌. (post 방식)
	@PostMapping(value = "/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		// @valid : 유효성을 체크하려는 객체(MemberFormDto) 앞에 붙여주는 
		// BindingResult : 유효성 검증 후에 결과를 넣어준다. 
		
		// memberform.html th:object=${memberFormDto}에서 알아서 값을 보내주니까
		// 여기서 받아서 bindingResult에 검증한 것이 결과가 남게 된다.
		
		
		if(bindingResult.hasErrors()) {
			//MemberFormDto 유효성 체크에서 하나라도 에러가 검증될 경우
			return "member/memberForm"; //알아서 memberform.html에 들어갈 에러메세지를 띄워준다
		}
		
		//에러가 발생할 때를 감안하여 코드를 일부 수정할거임.
		
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			// 멤버를 create를 만들어주고, member객체와 비밀번호를 넣어준다.
			memberService.saveMember(member);
					
		} catch (IllegalStateException e) {
			// 중복체크에 걸려서 에러가 발생했을 경우, 여기서 에러를 받아주고 
			model.addAttribute("errorMessage",e.getMessage());
			// memberform.html script에 [[${errorMesage}]]로 들어갈 수 있도록 이름을 지정해준다.
			return "member/memberForm";
		}
		// 특별한 문제가 없을 경우. '/'으로 리다이렉트 시켜준다.
		return "redirect:/"; 
		// insert를 하는 (데이터를 수정 & 등록) 하는 아이이기 때문에 redirect를 해줘야한다.
		// 이후 모델 안에 있던 data를 밀어버리고 다시 '/'으로 redirect하게 된다.
		// main '/'로 가는 걸 지정해줬으니 메인페이지 전용 controller를 다시 만들어야한다.
	}
	
	
	// 로그인 화면
	@GetMapping(value = "/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}
	
	
	// 로그인 에러시 발생시킬 페이지 (securityconfig 에서 넘겨받은거!)
	@GetMapping(value = "/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
	 //memberLoginForm 에서 에러를 받아낼 수 있도록 거기서 선언한 "${loginErrorMsg}"에 맞춰 에러값을 넣어준다.
		return "member/memberLoginForm";
	}
	
	
	private final SessionManager sessionManager; //의존성 주입
	
	
//	// 쿠키, 세션 테스트
//	@PostMapping(value = "/login2")
//	public String loginMember2(HttpServletResponse response, HttpSession session, @RequestParam String email) { 
//		//세션을 저장할 수 있는 HttpSession 객체 , email 주소를 알아서 가져와주는 @RequestParam String email.
//		System.out.println("email : "+ email);
//		Cookie idCookie = new Cookie("userCookieId", email);
//		response.addCookie(idCookie); // id를 쿠키에 저장함.
//		
//		//setAttribute 통해 session 객체 그 자체를 저장
//		// session.setAttribute("userSessionId2", email);
//		
//		sessionManager.createSession("sessionPerson2", response); // sessionPerson2라는 값을 넣어줌
//		// sessionManager 을 통해 나중에 세션의 쿠키를 통해서 값을 가져올 수도 있다.
//		
//		return "member/memberLoginForm";
//	}
	
	

	
}
