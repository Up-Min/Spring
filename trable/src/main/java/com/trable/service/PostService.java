package com.trable.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trable.dto.PostFormDto;
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
	
	public Long savePost(PostFormDto postFormDto, List<MultipartFile> postImgFileList) throws Exception {
		Post post = postFormDto.createPost();
		postRepository.save(post);
		
		for(int i=0; i<postImgFileList.size(); i++) {
			PostImg postImg = new PostImg();	
			postImgService.savePostImg(postImg, postImgFileList.get(i));
		}
		return post.getId();
	}
	
	
}
