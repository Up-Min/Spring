package com.myshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // 스프링에서 설정 클래스로 사용하겠다는 annotation 선언! + beans를 쓰려면 필요하다!
@EnableWebSecurity // 얘를 선언하면 springSecurityFilterchain이 자동으로 포함된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 원래 있던 것을 지움. 이렇게 하면 요청할 때 인증을 요구하지 않는다.
		// configure : http 요청에 대한 보안을 설정한다. (페이지 권한 설정, 로그인 페이지, 로그아웃 메소드 등에 대한 설정)	
	}
	@Bean //얘를 bean으로 쓰겠다는 의미. 객체를 제공해주는 역할을 한다!
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // 암호화를 위해서 사용하는 함수.
		// 비밀번호를 저장 할 때, DB에 저장이 되기는 하지만 보안때문에 암호화가 되서 저장이 된다.
		// 비밀번호 암호화를 위해서 사용하는 빈 (bean, 객체)
		// 암호화는 이러한느낌으로 사용하는 구나! 
	}
	
	
	
}
