package com.myshop.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myshop.dto.ItemFormDto;
import com.myshop.dto.ItemSearchDto;
import com.myshop.entity.Item;
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
			// Controller -> service
			itemService.saveItem(itemFormDto, itemImgFileList);
		}catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
			// itemForm.html의 script에서 받을 수 있게 "errorMessage"라는 이름으로 던져준다.
			return "item/itemForm";
		}
		return "redirect:/";
	}
	
	
	// 상품 수정 페이지 띄워주기
	@GetMapping(value = "/admin/item/{itemId}") 
	//itemId는 어떻게 바뀔까? 매개변수에 반영해준다.
	public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
		// @PathVariable : itemId가 path의 값이 되게 해준다.
		
		//ItemService에 있는 itemFormDto를 가져와서 model에 addAttribute를 할거다.
		try {
			//@PathVariable 를 통해 가져온 itemId,
			// 해당 item의 이미지와 정보를 가져올 수 있게 해주기 위해 
			// getItemDtl에 itemId를 넣어준다.
			ItemFormDto itemFormDto = itemService.getItemDtl(itemId); 
			model.addAttribute(itemFormDto);
			
		} catch (EntityNotFoundException e) {
			// 에러 발생시 html에 띄워줄 "errorMessage" 입력. (itemForm.html - script)
			model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
			// 빈 itemFormDto 객체를 던져줘야, html의 th:object에서 상품 설명 등등을 인식시킬 수 있다!
			model.addAttribute("itemFormDto", new ItemFormDto());
			return "item/itemForm";
		}
		return "item/itemForm";
	}
	
	//상품 수정 (수정버튼 클릭 시)
	@PostMapping(value = "/admin/item/{itemId}")
	public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
			@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
		// 유효성 검사가 필요하기 때문에, 상품 등록 할 때 썼던(itemNew에 있던) 거를 그대로 쓸거다.
		
		// 에러시 돌아가게 하는 것과, 필수 입력값이 있는지도 동일하게 itemNew에서 가져와서 확인한다.
		
		if(bindingResult.hasErrors()) {
			return "item/itemForm"; // 링크 이동
		}
		
		// 첫번째 이미지 (필수 입력값) 이 있는지, itemForm.html에서 넘어온 "itemFormDto"의 id 값이 null 인지 검사.
		if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
			// itemForm.html의 script에서 받을 수 있게 "errorMessage"라는 이름으로 던져준다.
		}
		
		// 서비스에 있는 updateItem 작동, 에러발생시 에러메세지 출력
		try {
			itemService.updateItem(itemFormDto, itemImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 수정 중 에러 발생하였습니다.");
		}
		
		return "redirect:/";
	}
	
	@GetMapping(value = {"/admin/items", "/admin/items/{page}"})
	// {} 안에 있는 주소로 들어오면 여기로 이어질 수 있게 셋팅해준다.
							// itemSearchDto, page의 pathvariable을 해줘야한다. 
	public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
		// url경로에 페이지가 있으면 해당 페이지를 조회하도록 하고, 페이지 번호가 없으면 0페이지를 조회하도록 한다.
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3); 
				//of(조회할 페이지의 번호 (삼항연산자 - 페이지 번호 (1,2,3)을 누를 때 {page}의 값이 대체된다. 즉, page에서 해당 조회할 번호가 있으면 띄워준다.)
				// , 한 페이지당 조회할 데이터의 갯수)
		
		Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
		// 페이징 처리를 한 pageable이기 때문에 List가 아닌 Page에 담아줘야 한다.
		
		int test = pageable.getPageSize();
		model.addAttribute("test",test);
		model.addAttribute("items", items);
		model.addAttribute("itemSearchDto", itemSearchDto);
		model.addAttribute("maxPage",5); // 상품 관리 메뉴 하단에 최대로 보여줄 페이지
		
		return "item/itemMng";
	}
	
}
