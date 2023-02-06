package com.trable.repository;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;

import com.trable.dto.PostSearchDto;
import com.trable.entity.Post;

public interface PostRepositoryCustom {

	Page<Post> getItemPage(PostSearchDto postSearchDto, Pageable pageable);
	
	
	
}
