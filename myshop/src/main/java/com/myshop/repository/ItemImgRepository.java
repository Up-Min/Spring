package com.myshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myshop.entity.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long>{
	// find 메소드 하나 만들기 (수정페이지 띄우기 위한 값 찾기용)
	List<ItemImg> findByItemIdOrderByIdAsc(Long itemId); //itemId로 값을 찾아온다.
}
