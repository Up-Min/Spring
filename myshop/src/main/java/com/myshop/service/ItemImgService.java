package com.myshop.service;

import javax.persistence.EntityNotFoundException;
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
	
	// 이미지 저장 메소드
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
		// ItemImg table안에 값을 저장해줄거임.
		String oriImgName = itemImgFile.getOriginalFilename(); //파일의 원래 이름을 가져온다.
		String imgName = "";
		String imgUrl = "";
		
		// 파일 업로드 진행
		if(!StringUtils.isEmpty(oriImgName)) { 
			//StringUtils - 문자열이 null이거나 빈 문자열을 잡아준다. 즉, 그게 아닐때!
			//파일의 원본이름이 빈 문자열이나 null이 아니면 업로드를 진행한다.
			// ITEM IMG SERVICE -> FILE SERVICE
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			// 경로는 /Users/l/shop/item, 파일의 원래 이름, 파일의 데이터 (byte로 된) 자체를 넘겨준다.
			imgUrl = "/images/item/" + imgName;
		}
		
		// 상품 이미지 정보 저장
		
		// ENTITY
		itemImg.updateItemImg(oriImgName, imgName, imgUrl); 
		// SERVICE -> REPOSITORY
		itemImgRepository.save(itemImg);
	}
	
	// 이미지 업데이트 메소드
	public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
		// 이미지 파일이 비어있지 않으면, 뭔가 파일이 있으면!
		if(!itemImgFile.isEmpty()) {
			// itemImgId를 가져와서 itemImgRepository의 findById에 집어넣는다! (이미지 레코드를 찾아온다!)
			// ITEM IMG SERVICE -> ITEM IMG REPOSITORY
			ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);
			
			// 기존 이미지 파일 삭제 /shop/item에 저장되었던 기존 이미지들을 삭제해준다.
			if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
				// DB에 저장해뒀었던 getImgName가 비어있거나 null이 아니면 삭제한다!
				// ITEM IMG SERVICE -> FILE SERVICE
				fileService.deleteFile(itemImgLocation+"/"+savedItemImg.getImgName()); 
				// 파일을 건드릴 수 있는 fileService를 통해 //Users/l/shop/item에 있는 
				// 0b51e5b1-fbcb-436e-beee-64e3252fcd54.jpg를 지워준다.
			}
			
			// 수정된 이미지 업로드 - 위에서 사용했던 이미지 저장 메소드를 그대로 가져온다.
			String oriImgName = itemImgFile.getOriginalFilename(); 
			String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			String imgUrl = "/images/item/" + imgName;
			
			
			// 상품 이미지 정보 저장 (ItemImg에 있는 updateItemImg 메소드 이용)
			savedItemImg.updateItemImg(oriImgName, imgName, imgUrl); 
			// 수정할 때는 .save없이 여기서 끝내준다.
			// 왜? (짱 중요함) 영속성컨텍스트에 이미 이 엔티티가 들어가 있다! 
			// savedItemImg가 이미 영속 상태이다. 그래서 데이터를 변경하는 것만으로도 
			// 변경감지 기능이 동작하여 트랜잭션이 끝날때 update쿼리가 작동된다.
			// 이거는 엔티티가 반드시 영속상태여야만 가능하다. 최초 선언시에 한번 save를 해줬으니까! 
		}
		
	}
	
}
