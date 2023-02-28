package com.myshop.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.dto.ItemSearchDto;
import com.myshop.dto.MainItemDto;
import com.myshop.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final ItemService itemservice;
	
	@GetMapping(value = "/")
	public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent()?page.get(): 0 , 6); 
		//pageable 인터페이스의 자식이 pageRequest, of : 0, 6 (페이지의 인덱스 번호, 0부터 시작할거고, 6개를 한번에 보여줄거다.)
		// 페이지 1,2,3에 따라 페이징 번호가 따라가게 하기 위해 (0,6)에서 삼항연산자로 0 부분을 고쳐준다.
		// url경로에 페이지 번호가 있으면 그대로 출력한다 (페이지 번호가 1일때 1, 2일때 2)
		// 페이지 번호가 없으면 0을 출력한다. 처음에는 1페이지를 보여줄거니까
		
		Page<MainItemDto> items = itemservice.getMainItemPage(itemSearchDto, pageable);
		// pageRequest -> pageable<interface>
		
		model.addAttribute("items",items); // 서비스에서 받아온 DB데이터 값 저장.
		model.addAttribute("itemSearchDto",itemSearchDto); // Dto 저장.
		model.addAttribute("maxPage", 5); // 최대 페이지 지정
		
		return "main";

	}
	
	// itemservice getMainItemPage에서 가져온 값이 offset,limit을 통해 정렬되고, new PageImpl을 리턴하고
	// 얘를 Page<MainItemDto> items 여기서 받아온다.
	
}
