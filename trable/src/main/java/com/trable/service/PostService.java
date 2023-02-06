package com.trable.service;

import java.awt.print.Pageable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trable.dto.PostFormDto;
import com.trable.dto.PostSearchDto;
import com.trable.entity.Post;
import com.trable.entity.PostImg;
import com.trable.repository.PostImgRepository;
import com.trable.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

	private final PostRepository postRepository;
	private final PostImgRepository postImgRepository;
	private final PostImgService postImgService;
	private final PostMainImgService postMainImgService;
	
	public Long savePost(PostFormDto postFormDto, List<MultipartFile> postImgFileList, MultipartFile postMainImg) throws Exception {
		Post post = Post.createPost(postFormDto);
		postMainImgService.savememberImg(post, postMainImg);
		postRepository.save(post);
		for(int i=0; i<postImgFileList.size(); i++) {
			PostImg postImg = new PostImg();	
			postImg.setPost(post);
			postImgService.savePostImg(postImg, postImgFileList.get(i));
		}
		return post.getId();
	}

	
	public Page<Item> getPostPage(PostSearchDto postSearchDto, Pageable pageable){
		return postRepository
	}
	
}
