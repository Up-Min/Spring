package com.trable.service;

import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trable.entity.Member;
import com.trable.entity.Post;
import com.trable.entity.PostImg;
import com.trable.repository.PostImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostMainImgService {
	@Value("${ImgLocation}")
	private String imgLocation;
	private final PostImgRepository postImgRepository;
	private final FileService fileService;
	
	public void savememberImg(Post post, MultipartFile imgfile) throws Exception {
	
		String oriImgName = imgfile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(imgLocation, oriImgName, imgfile.getBytes());
			imgUrl = "/image/img/"+imgName;
		}
		post.updatePostImg(oriImgName, imgName, imgUrl);	
	}
	
	
	public void updatePostMainImg(Post post, MultipartFile imgfile) throws Exception {
				
		if(!StringUtils.isEmpty(post.getImgname())) {
			fileService.deleteFile(imgLocation+"/"+post.getImgname());
		}

		String oriImgName = imgfile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(imgLocation, oriImgName, imgfile.getBytes());
			imgUrl = "/image/img/"+imgName;
		}
		post.updatePostImg(oriImgName, imgName, imgUrl);	
		
		
	}
}
