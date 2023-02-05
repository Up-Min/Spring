package com.trable.dto;


import org.modelmapper.ModelMapper;

import com.trable.entity.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostFormDto {

	private String postname;
	
	private int like;
	
	private String imgori;
	
	private String imgname;
	
	private String imgurl;
	
	private static ModelMapper modelMapper;
	
	public Post createPost() {
		return modelMapper.map(this, Post.class);
	}
	
	
	
}
