package com.myshop.dto;

import org.modelmapper.ModelMapper;

import com.myshop.entity.ItemImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ItemImgDto {
	private Long id;
	
	private String imgName; // 이미지 파일명
	
	private String oriImgName; // 원본 이미지 파일명
	
	private String imgUrl; // 이미지 조회 경로
	
	private String repimgYn; // 대표 이미지 여부
	
	private static ModelMapper modelMapper = new ModelMapper();
	// 멤버변수로 ModelMapper 하나 추가.
	
	public static ItemImgDto of(ItemImg itemImg) {
		return modelMapper.map(itemImg, ItemImgDto.class);
		// map? 지도 - 찾아주는 역할. itemImg 엔티티 ~ DTO로 변환을 시킬 때 빠르게 변환할 수 있게 도와준다.
		
	}
}
