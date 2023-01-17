package com.myshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.myshop.service.MemberService;

@Configuration // 스프링에서 설정 클래스로 사용하겠다는 annotation 선언! + beans를 쓰려면 필요하다!
@EnableWebSecurity // 얘를 선언하면 springSecurityFilterchain이 자동으로 포함된다.
public class SecurityConfig {
	
	@Autowired
	MemberService memberService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 로그인에 대한 설정
		http.formLogin()
		.loginPage("/members/login") //로그인 페이지 url 설정
		.defaultSuccessUrl("/") // 로그인 성공시 이동할 페이지
		.usernameParameter("email") // 로그인시 사용할 파라메터 이름
		.failureUrl("/members/login/error") // 로그인 실패시 이동할 url
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 url
		.logoutSuccessUrl("/"); //로그아웃 성공시 이동할 url
		
		return http.build();
	}
	
	
	@Bean //얘를 bean으로 쓰겠다는 의미. 객체를 제공해주는 역할을 한다!
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // 암호화를 위해서 사용하는 함수.
		// 비밀번호를 저장 할 때, DB에 저장이 되기는 하지만 보안때문에 암호화가 되서 저장이 된다.
		// 비밀번호 암호화를 위해서 사용하는 빈 (bean, 객체)
		// 암호화는 이러한느낌으로 사용하는 구나! 
	}
	
	
	
}
