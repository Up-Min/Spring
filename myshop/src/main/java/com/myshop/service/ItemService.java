package com.myshop.service;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.myshop.dto.ItemFormDto;
import com.myshop.dto.ItemImgDto;
import com.myshop.dto.ItemSearchDto;
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
		// ENTITY
		Item item = itemFormDto.createItem(); //html페이지에서 입력받은 itemFormDto가 들어가 있다.
		
		// SERVICE -> REPOSITORY
		itemRepository.save(item); //itemRepository를 통해 DB에 넣어준다.
		
		//이미지 등록 
		for(int i=0; i<itemImgFileList.size(); i++) { //이미지 갯수에 따라 돌아간다.
			// ENTITY
			ItemImg itemImg = new ItemImg(); // ItemImg 객체 선언
			itemImg.setItem(item); //html페이지에서 입력받은 itemFormDto를 객체에 넣어준다.
			
			if(i==0) { //첫번째 순서의 이미지일 때 대표 상품 이미지 여부 지정;
				itemImg.setRepimgYn("Y"); 
			}else {
				itemImg.setRepimgYn("N");
			}
			// ITEM SERVICE -> ITEM IMG SERVICE
			itemImgService.saveItemImg(itemImg, itemImgFileList.get(i)); //상품의 이미지 정보 저장
			//itemImg table(itemImgFileList.get(i))에 아이템 이미지 정보(itemImg)가 등록 됨 
		}
		return item.getId(); //item에서 getId를 한것을 리턴함.
	}
	
	// 상품 가져오기 (수정페이지에 띄울 상품정보)
	@Transactional(readOnly = true) 
	// 문제가 생기면 롤백시키는 @Transactional, readOnly를 넣으면 읽기 전용이 됨. 
	// 어차피 select구문만 있으니 굳이 변경감지를 할 필요가 없다. 성능 향상을 위해 readonly를 사용한다.
	public ItemFormDto getItemDtl(Long itemId) {
		
		// 1. item_img 테이블의 이미지를 가져온다.
		
		// SERVICE -> REPOSITORY -> SERVICE
		List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId); 
		// 리포지토리에서 만들었던 find메소드 실행
		// itemId로 select를 돌려 5개를 가져올 것이다.

		// itemImgDtoList선언
		
		// SERVICE -> DTO -> SERVICE
		List<ItemImgDto> itemImgDtoList = new ArrayList<>();
		
		// 가져온 itemImglist를 (최대 5개) itemImgDtoList로 변환시켜줄거다. (엔티티 객체 -> dto객체 변환)
		
		// SEPARATE IMG FROM IMGLIST From REPOSITORY 
		// -> CHANGE IMG TO IMGDTO BY USING MAPPER (REPEAT IMGLIST'S SIZE)
		// -> ADD CHANGED IMGDTO TO IMGDTOLIST 
		for(ItemImg itemImg : itemImgList) {
			// 엔티티 - dto클래스를 효율적으로 변환시켜주는 modelMapper - of메소드 를 사용할거다.
			ItemImgDto itemImgDto = ItemImgDto.of(itemImg); // - 엔티티 명만 맞춰주면 알아서 데이터를 Dto에 맵핑해주는 of.
			// itemImgDtoList에 변환한 itemImgDto를 넣어준다.
			itemImgDtoList.add(itemImgDto);
		}
		
		// 2. item 테이블에 있는 데이터를 가져올거다.
		
		// SERVICE -> REPOSITORY(FIND BY) -> SERVICE (ITEM ENTITY) 
		Item item = itemRepository.findById(itemId)
					.orElseThrow(EntityNotFoundException::new);
				//리턴 타입이 옵셔널이기 때문에 항상 elseThrow로 에러를 던져줘야한다.
		
		// 엔티티 객체 -> dto객체 변환 - by mapper
		// ITEM -> ITEMFORMDTO
		ItemFormDto itemFormDto = ItemFormDto.of(item);
		
		// 1에서 만들어놨던 itemImgDtoList를 넣어준다.
		// itemFormDto에는 이미지 5개를 넣을 수 있는 list가 있었다.
		
		// ADD IMGDTOLIST INTO ITEMFORMDTO
		itemFormDto.setItemImgDtoList(itemImgDtoList);
		
		// RETURN ITEMFORMDTO (TO REPLACE VALUES AT VIEW PAGE)
		return itemFormDto; // 결과적으로 item 정보에 itemImg를 넣어서 함께 보내준다.
		// 서비스를 구현해놓고 controller에 request요청할거임.
	}
	
	// 상품 수정 메소드
	public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
		// 상품의 아이디 값을 조회. 영속성 컨텍스트에 이미 있는 아이이다.
		
		// SERVICE -> REPOSITORY(FIND BY) -> SERVICE (ITEM ENTITY)
		Item item = itemRepository.findById(itemFormDto.getId()).
					orElseThrow(EntityNotFoundException::new);
		
		// 수정한 데이터 값을 넣어준다.
		// ?? SERVICE -> ENTITY ??
		item.updateItem(itemFormDto);
	
		// 최대 5개까지 들어갈 수 있는 ItemImgId들의 리스트를 조회.
		
		// CONTROLLER -> SERVICE -> LIST (BY DTO)
		List<Long> itemImgIds = itemFormDto.getItemImgIds();
		
		// 이미지 파일 리스트의 갯수(길이) 만큼 값을 반복시켜준다.
		// ITEM SERVICE -> ITEM IMG SERVICE
		for(int i=0; i<itemImgFileList.size(); i++) {
			itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
			// 이미지의 아이디를 가져오고, 이미지 파일 그 자체를 가져온다.
			// 이거를 실행하면 DB에 바로 업데이트가 된다.
		}
		return item.getId();
	}
	
	// 상품 리스트 가져오기
	@Transactional(readOnly = true) 
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
		// 상품 정보와 페이징 처리하는 애들을 가져와서 리포지토리에 넣어준다.
		// ITEM SERVICE -> ITEM REPOSITORY
		return itemRepository.getAdminItemPage(itemSearchDto, pageable);	
	}
}
