package com.trable.dto;

import org.modelmapper.ModelMapper;

import com.trable.entity.PostImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostImgDto {

	private Long id;
	
	private String imgname;
	
	private String imgurl;

	private String imgori;

	private static ModelMapper modelMapper = new ModelMapper();
	
	private static PostImgDto of (PostImg postImg) {
		return modelMapper.map(postImg, PostImgDto.class);
	}
	
	
}
