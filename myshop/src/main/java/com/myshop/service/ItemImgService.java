package com.myshop.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.myshop.entity.ItemImg;
import com.myshop.repository.ItemImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
	
	@Value("${itemImgLocation}") //프로퍼티에 있는 #상품 이미지 업로드 경로 itemImgLocation 을 가져올거임.
	private String itemImgLocation; // /Users/l/shop/item
	
	private final ItemImgRepository itemImgRepository;
	
	private final FileService fileService;
	
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
		// ItemImg table안에 값을 저장해줄거임.
		String oriImgName = itemImgFile.getOriginalFilename(); //파일의 원래 이름을 가져온다.
		String imgName = "";
		String imgUrl = "";
		
		// 파일 업로드 진행
		if(!StringUtils.isEmpty(oriImgName)) { 
			//StringUtils - 문자열이 null이거나 빈 문자열을 잡아준다. 즉, 그게 아닐때!
			//파일의 원본이름이 빈 문자열이나 null이 아니면 업로드를 진행한다.
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			// 경로는 /Users/l/shop/item, 파일의 원래 이름, 파일의 데이터 (byte로 된) 자체를 넘겨준다.
			imgUrl = "/images/item/" + imgName;
		}
		
		// 상품 이미지 정보 저장
		itemImg.updateItemImg(oriImgName, imgName, imgUrl); 
		itemImgRepository.save(itemImg);
	}
}
