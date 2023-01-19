package com.myshop.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.myshop.constant.ItemSellstatus;
import com.myshop.entity.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ItemFormDto {
	// item table을 이미 Item엔티티 에서 만들었으니 이걸가져오자.
	// + MemberFormDto 참고하여 유효성 체크 하기

	private Long id; // 상품코드
	
	@NotBlank (message = "상품명은 필수 입력 값입니다.")
	private String itemNm; // 상품명

	@NotNull (message = "가격은 필수 입력 값입니다.")
	private int price; // 가격

	@NotNull (message = "재고수량은 필수 입력 값입니다.")
	private int stockNumber; // 재고수량
	
	@NotBlank (message = "상품 상세설명은 필수 입력 값입니다.")	
	private String itemDetail; // 상품 상세설명
	
	private ItemSellstatus itemSellStatus; // 상품 판매상태
	
	

	// 이미지 넣기
	private List<ItemImgDto> itemImgDtoList = new ArrayList<>(); // 상품 이미지 정보를 저장하는 리스트
	
	private List<Long> itemImgIds = new ArrayList<>(); 
	// 상품에 이미지 아이디를 저장 : 수정할 때 이미지 아이디를 담아두려는 목적임.
	
	private static ModelMapper modelMapper = new ModelMapper();
	// 멤버변수로 ModelMapper 하나 추가.
	
	public Item createItem() {
		return modelMapper.map(this, Item.class);
	}
	
	public static ItemFormDto of(Item item) {
		return modelMapper.map(item, ItemFormDto.class);
	}

	
	

}
