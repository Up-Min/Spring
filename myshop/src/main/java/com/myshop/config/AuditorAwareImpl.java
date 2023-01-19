package com.myshop.config;

import java.util.Optional;


import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


// AuditorAware : 로그인 한 사용자의 정보를 등록자와 수정자로 지정. 
public class AuditorAwareImpl implements AuditorAware<String>{
	@Override
	public Optional<String> getCurrentAuditor() {

		// 현재 로그인한 사용자의 정보를 가져온다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String userId = "";
		
		if(authentication != null) {
			userId = authentication.getName(); 
			// 사용자의 이름을 가져온다. (사용자의 이름은 email로 최초 선언했기 때문에 email을 받을거임)
		}
		return Optional.of(userId);
		// 사용자의 이름을 등록자와 수정자로 지정해준다. (스프링에서 지정해준다. 이름만 등록해두면 된다!)
	}
	
	
	
}
