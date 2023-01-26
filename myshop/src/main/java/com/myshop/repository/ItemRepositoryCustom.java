package com.myshop.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myshop.dto.ItemSearchDto;
import com.myshop.dto.MainItemDto;
import com.myshop.entity.Item;

//사용자 정의 인터페이스
public interface ItemRepositoryCustom {
	// 관리자 페이지에 뿌리는 아이템 (상품관리)
	Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
	// 내가 검색한것을 입력을 해서 페이징 처리해서 받아올거임.
	// pageable : 상품판매상태 등등을 담아서 결과를 얻어온다. (검색을 누르면 list에 해당 결과가 나온다)
	
	
	// MainItemDto에 붙일걸로 추상메소드 추가 - 메인화면에 뿌리는 item
	Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
	
	
}
