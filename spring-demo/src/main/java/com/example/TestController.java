package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	
	@GetMapping(value = "/test")
	public UserDTO test() {
		UserDTO userdto = new UserDTO();
		// 롬복으로 가져온 setter 사용
		userdto.setName("일상민");
		userdto.setAge(29);
		
		//System.out.println("객체 userdto 정보 : "+userdto.toString());		
		return userdto;
	}
	
	
}
