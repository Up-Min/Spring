package com.myshop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myshop.dto.ItemFormDto;
import com.myshop.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {
	
	private final ItemService itemService;
	
	// 상품등록 페이지를 보여주는 메소드
	@GetMapping(value = "/admin/item/new")
	public String itemForm(Model model) {
		model.addAttribute("itemFormDto", new ItemFormDto()); 
		//itemForm.html에서 넘어온 "itemFormDto" 를 받아서 저장.
		return "item/itemForm";
	}
	
	//상품을 등록하는 메소드
	@PostMapping(value = "/admin/item/new")
	//itemForm.html에서 저장 버튼을 누르면 action을 통해 여기로 온다.
	public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
			@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
		// 유효성 검사에 필요한 @Valid ItemFormDto itemFormDto, BindingResult bindingResult,
		// itemForm.html에서 넘어오는 상품 이미지 파일의 이름 "itemImgFile"을 받음 
		// 얘네를 List형태로 받아서 MultipartFile타입으로 받는다 
		
		if(bindingResult.hasErrors()) {
			return "item/itemForm"; // 링크 이동
		}
		
		// 첫번째 이미지 (필수 입력값) 이 있는지, itemForm.html에서 넘어온 "itemFormDto"의 id 값이 null 인지 검사.
		if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
			// itemForm.html의 script에서 받을 수 있게 "errorMessage"라는 이름으로 던져준다.
		}
		
		try{
			itemService.saveItem(itemFormDto, itemImgFileList);
		}catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
			// itemForm.html의 script에서 받을 수 있게 "errorMessage"라는 이름으로 던져준다.
			return "item/itemForm";
		}
		return "redirect:/";
	}
	
	
	
	
	
	
	
}
