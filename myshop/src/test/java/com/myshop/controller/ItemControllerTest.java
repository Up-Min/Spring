package com.myshop.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	@DisplayName ("상품 등록 페이지 권한 테스트")
	@WithMockUser(username = "admin", roles = "ADMIN") 
	// 가상의 유저 하나를 만들어 줌. - 유저가 로그인 된 상태로 테스트 할 수 있게 해준다.
	public void itemFormTest() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new")) //이 페이지로 get방식, 리퀘스트를 한다.
		.andDo(print()) //요청과 응답 메세지를 콘솔에 출력.
		.andExpect(status().isOk()); // 응답상태 코드가 정상인지 여부를 확인.
	}
	
	@Test
	@DisplayName ("상품 등록 페이지 일반 회원 접근 테스트")
	@WithMockUser(username = "admin", roles = "USER") 
	// 유저의 role를 "USER" 로 바꿔줌.
	public void itemFormNotAdminTest() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new")) 
		.andDo(print()) 
		.andExpect(status().isForbidden()); // 응답상태 코드가 정상이 아니면(forbidden 예외가 발생하면) 통과.
	}
}
