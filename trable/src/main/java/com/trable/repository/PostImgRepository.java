package com.trable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trable.entity.PostImg;

public interface PostImgRepository extends JpaRepository<PostImg, Long>{

		List<PostImg> findByPostId(Long postid);	
}
