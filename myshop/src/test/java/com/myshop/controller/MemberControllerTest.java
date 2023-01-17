package com.myshop.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.dto.MemberFormDto;
import com.myshop.entity.Member;
import com.myshop.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc // mockup(목업) 실제 객체와 비슷하지만 테스트에 필요한 기능만 제공하는 가짜 객체 생성.
// -> 웹부라우저에서 요청을 하는 것처럼 작성이 가능함.

@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Member createMember(String email, String password) {
		MemberFormDto member = new MemberFormDto(); // 생성자 member를 가져옴.
		member.setName("홍");
		member.setEmail(email);
		member.setAddress("홍익대학교");
		member.setPassword(password);
		
		Member member2 = Member.createMember(member, passwordEncoder);
		
		
		return memberService.saveMember(member2);
		//memberservice 의 savenumber -> table 추가.		
	}
	
	@Test
	@DisplayName("로그인 성공 테스트")
	public void loginSuccessTest() throws Exception{
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);
        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login") //회원가입 메소드를 실행 후에 회원정보로 로그인 되는지 테스트. (해당 url로 로그인 요청)
                .user(email).password(password)) // 테스트 할 이메일, 비밀번호를 넣으면 된다. 
                .andExpect(SecurityMockMvcResultMatchers.authenticated()); 
        		// 그리고 기대하다? 로그인이 성공해서 인증되면 테스트 코드를 통과시킨다.
        
	}
	
	@Test
	@DisplayName("로그인 실패 테스트")
	public void loginFailTest() throws Exception{
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);
        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login") //회원가입 메소드를 실행 후에 회원정보로 로그인 되는지 테스트. (해당 url로 로그인 요청)
                .user(email).password("12345")) // 일부러 실패를 만들어줌.
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated()); 
        		// 얘는 실패했을 때 테스트 코드를 통과시킨다.
	}
	

}
