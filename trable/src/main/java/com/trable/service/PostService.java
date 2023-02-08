package com.trable.service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trable.dto.PostFormDto;
import com.trable.dto.PostImgDto;
import com.trable.dto.PostSearchDto;
import com.trable.entity.Member;
import com.trable.entity.Post;
import com.trable.entity.PostImg;
import com.trable.repository.MemberRepository;
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
	private final MemberRepository memberRepository;
	
	public Long savePost(PostFormDto postFormDto, List<MultipartFile> postImgFileList, MultipartFile postMainImg, String email) throws Exception {
		Member member = memberRepository.findByEmail(email);
		
		Post post = Post.createPost(member, postFormDto);
		
		postMainImgService.savememberImg(post, postMainImg);
		postRepository.save(post);
		for(int i=0; i<postImgFileList.size(); i++) {
			PostImg postImg = new PostImg();	
			postImg.setPost(post);
			postImgService.savePostImg(postImg, postImgFileList.get(i));
		}
		return post.getId();
	}
	
	public Long updatePost(PostFormDto postFormDto, List<MultipartFile> postImgFileList, 
			MultipartFile postMainImg, String email, Long postid) throws Exception {
		Member member = memberRepository.findByEmail(email);
		Post post = postRepository.findById(postid).orElseThrow(EntityNotFoundException::new);
		List<PostImg> postimglist = postImgRepository.findByPostId(postid);
		
		post.updatePost(member, postFormDto);
		postMainImgService.updatePostMainImg(post, postMainImg);
		
		for(int i=0; i<postImgFileList.size(); i++) {
			postImgService.updatePostImg(postimglist.get(i),postimglist.get(i).getId(),postImgFileList.get(i));
		}
		
		return post.getId();
	}
	
	
	public PostFormDto getPostDto(Long postId) {
		Post post = getPostbyid(postId);
		PostFormDto postFormDto = PostFormDto.of(post);
		return postFormDto;
	}
	public List<Post> getPostPage(){
		return postRepository.findAll();
	}
	
	public List<Post> getUserPost(Member member){
		return postRepository.findByMember(member);
	}
	public Post getPostbyid(Long postid){
		 Post post = postRepository.findById(postid).orElseThrow(EntityNotFoundException::new);
		 return post;
	}
	}
