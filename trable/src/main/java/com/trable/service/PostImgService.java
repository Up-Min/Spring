package com.trable.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trable.entity.PostImg;
import com.trable.repository.PostImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostImgService {

	@Value("${ImgLocation}")
	private String postImgLocation;
	private final PostImgRepository postImgRepository;
	private final FileService fileService;
	
	public void savePostImg(PostImg postImg, MultipartFile postimgfile) throws Exception {
		
		String oriImgName = postimgfile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(postImgLocation, oriImgName, postimgfile.getBytes());
			imgUrl = "/image/data/img/"+imgName;
		}
		
		postImg.updateImg(oriImgName, imgName, imgUrl);
		postImgRepository.save(postImg);	
	}
	
	public void updatePostImg(PostImg postImg, Long postImgid, MultipartFile postimgfile) throws Exception {
		
		if(!postimgfile.isEmpty()) {
			PostImg savedPostImg = postImgRepository.findById(postImgid)
					.orElseThrow(EntityNotFoundException::new);
			
			if(!StringUtils.isEmpty(savedPostImg.getImgname())) {
				fileService.deleteFile(postImgLocation+"/"+savedPostImg.getImgname());
			}
			
			String oriImgName = postimgfile.getOriginalFilename();
			String imgName = "";
			String imgUrl = "";
			
			if(!StringUtils.isEmpty(oriImgName)) {
				imgName = fileService.uploadFile(postImgLocation, oriImgName, postimgfile.getBytes());
				imgUrl = "/image/data/img/"+imgName;
			}
			
			postImg.updateImg(oriImgName, imgName, imgUrl);
			postImgRepository.save(postImg);	
			
		}
	}
	
	public List<PostImg> getPostimg(Long postid) {
		return postImgRepository.findByPostId(postid);
	}
	
}
