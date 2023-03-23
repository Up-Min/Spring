package com.trable.service;

import java.io.IOException;

import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trable.entity.Member;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class UserImgService {

	@Value("${ImgLocation}")
	private String imgLocation;
	
	private final FileService fileService;
	
	public void savememberImg(Member member, MultipartFile imgfile) throws Exception {
	
		String oriImgName = imgfile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";	
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(imgLocation, oriImgName, imgfile.getBytes());
			imgUrl = "/image/img/"+imgName;
		}
		member.updateImg(oriImgName, imgName, imgUrl);
	}
	
	public void updatememberImg(Member member, MultipartFile imgfile) throws Exception {
		// 그 전에 기존거 없애줘야함.
		member.updateImg("", "", "");
		String oriImgName = imgfile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";	
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(imgLocation, oriImgName, imgfile.getBytes());
			imgUrl = "/image/img/"+imgName;
		}
		member.updateImg(oriImgName, imgName, imgUrl);

		
		
	}
	
}
