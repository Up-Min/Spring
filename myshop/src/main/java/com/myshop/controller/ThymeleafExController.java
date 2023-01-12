package com.myshop.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myshop.dto.ItemDto;


@Controller // 컨트롤러의 역할을 하는 클래스를 정의 함.
@RequestMapping(value = "/thymeleaf") //url의 경로를 지정해주는 역할을 함.
// controller 주소 localhost를 사용하고 있는데, requestMapping을 달아놨기 때문에, 
// http://localhost/thymeleaf로 들어오게 된다.


public class ThymeleafExController {
		@GetMapping(value = "/ex01") // 주소의 경로 지정. 라우팅의 역할과 동일함!
		public String thymeleafEx01(Model model) { //Model? request와 비슷한 역할을 한다.
			model.addAttribute("data","타임리프 예제 입니다.");
			// request.setAttribute와 동일한 역할을 하는 코드가 됨.
			// spring에서는 model을 통해 데이터를 주고 받는다.
			
			return "thymeleafEx/thymeleafEx01";
			// html 페이지로 이어주는 역할을 한다.
			
		}
		
		@GetMapping(value = "/ex02")
		public String thymeleafEx02(Model model) {
			ItemDto itemDto = new ItemDto();
			
			itemDto.setItemNm("테스트 상품");
			itemDto.setPrice(10000);
			itemDto.setItemDetail("테스트 상품 상세 설명");
			itemDto.setRegTime(LocalDateTime.now());
			
			//model 넣어줄거다.
			model.addAttribute("itemDto",itemDto);
			
			return "thymeleafEx/thymeleafEx02";
			// 모델에 addAttribute한 데이터가 thymeleafEx02로 넘어가게 된다.
		}
		
		@GetMapping(value = "/ex03")
		public String thymeleafEx03(Model model) {
			//for each 사용해볼거임
			List<ItemDto> itemDtoList = new ArrayList<>(); 
			
			for (int i = 1; i<=10; i++) {
				ItemDto itemDto = new ItemDto();
				
				itemDto.setItemNm("테스트 상품" + i);
				itemDto.setPrice(10000+i);
				itemDto.setItemDetail("테스트 상품 상세 설명" +i);
				itemDto.setRegTime(LocalDateTime.now());
				itemDtoList.add(itemDto);
			}
			
			//model 넣어줄거다.
			model.addAttribute("itemDtoList",itemDtoList);
			
			return "thymeleafEx/thymeleafEx03";
		}
		
		@GetMapping(value = "/ex04")
		public String thymeleafEx04(Model model) {
			//if~unless (if~else) 사용해볼거임
			List<ItemDto> itemDtoList = new ArrayList<>(); 
			for (int i = 1; i<=10; i++) {
				ItemDto itemDto = new ItemDto();
				itemDto.setItemNm("테스트 상품" + i);
				itemDto.setPrice(10000+i);
				itemDto.setItemDetail("테스트 상품 상세 설명" +i);
				itemDto.setRegTime(LocalDateTime.now());
				itemDtoList.add(itemDto);
			}
			model.addAttribute("itemDtoList",itemDtoList);			
			return "thymeleafEx/thymeleafEx04";
		}
		
		@GetMapping(value = "/ex05")
		public String thymeleafEx05(Model model) {			
			return "thymeleafEx/thymeleafEx05";
		}
		
		@GetMapping(value = "/ex06")
		public String thymeleafEx06(String param1, String param2, Model model) {
			// Ex05에서 넘긴 파라미터 받기 : 메소드 안에 매개변수로 받으면 된다.
			model.addAttribute("param1", param1);
			model.addAttribute("param2", param2);
			return "thymeleafEx/thymeleafEx06";
		}
		
		@GetMapping(value = "/ex07")
		public String thymeleafEx07(Model model) {			
			return "thymeleafEx/thymeleafEx07";
		}
		
		
		
}
