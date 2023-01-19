package com.myshop.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myshop.dto.ItemFormDto;
import com.myshop.entity.Item;
import com.myshop.entity.ItemImg;
import com.myshop.repository.ItemImgRepository;
import com.myshop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

	private final ItemRepository itemRepository;
	private final ItemImgService itemImgService;
	private final ItemImgRepository itemImgRepository;
	
	
	// 상품 등록
	public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
		
		// 상품 (item) 등록
		Item item = itemFormDto.createItem(); //html페이지에서 입력받은 itemFormDto가 들어가 있다.
		itemRepository.save(item); //itemRepository를 통해 DB에 넣어준다.
		
		//이미지 등록 
		for(int i=0; i<itemImgFileList.size(); i++) { //이미지 갯수에 따라 돌아간다.
			ItemImg itemImg = new ItemImg(); // ItemImg 객체 선언
			itemImg.setItem(item); //html페이지에서 입력받은 itemFormDto를 객체에 넣어준다.
			
			if(i==0) { //첫번째 순서의 이미지일 때 대표 상품 이미지 여부 지정;
				itemImg.setRepimgYn("Y"); 
			}else {
				itemImg.setRepimgYn("N");
			}
			itemImgService.saveItemImg(itemImg, itemImgFileList.get(i)); //상품의 이미지 정보 저장
			//itemImg table(itemImgFileList.get(i))에 아이템 이미지 정보(itemImg)가 등록 됨 
		}
		return item.getId(); //item에서 getId를 한것을 리턴함.
		
		
		
	}
	
}
