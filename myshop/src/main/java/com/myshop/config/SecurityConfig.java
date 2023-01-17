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
	
		
		// 페이지 접근에 관한 설정 추가
		http.authorizeRequests()
			.mvcMatchers("/css/**","/js/**","/img/**").permitAll()
			.mvcMatchers("/","/members/**","/item/**","/images/**").permitAll() // 모든 사용자가 로그인 없이 접근할 수 있도록 설정 (경로를 지정해준다)
			// "/members/**" '**' members 하위에 있는 모든 접근을 포함시켜줌
			.mvcMatchers("/admin/**").hasRole("ADMIN") // role이 "ADMIN"인 사람만 "/admin"으로 시작하는 페이지에 접근할 수 있음.
			.anyRequest().authenticated(); //그 외의 페이지는 인증을 필요로 하도록 설정
		
		//인증되지 않은 사용자가 리소스(페이지, 이미지 등...)에 접근했을 때 설정
		// 클래스를 하나 더 만들어야함
		http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		//에러 핸들링 - 인증에 대한 에러 - 아까 만든 CustomAuthenticationEntryPoint.java에 있는 에러를 사용 - 401에러 리턴
		
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
