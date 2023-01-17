package com.myshop.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

// 인증하는 과정에서 예외(인증되지 않은 사용자)가 리소스를 요청 했을 때, 예외를 핸들링 시킴 (어떻게 처리할 것인가?)
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // 에러를 응답시켜준다. (401 에러코드를 만들어서 보내준다)
			//에러 상태 페이지를 보내려고 한다면, http 에러 코드에 맞춰서 에러코드를 보내줄 수 있어야한다. HttpServletResponse.SC_UNAUTHORIZED 가 몇번 코드인지에 대한 이해는 필요하다
			

	
	
	}
	
		
	
	
	
}
