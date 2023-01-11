package com.myshop.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.myshop.constant.ItemSellstatus;
import com.myshop.entity.Item;

@SpringBootTest // test에는 항상 넣어줘야한다.test class로 쓰겠다는 의미!
@TestPropertySource(locations = "classpath:application-test.properties")
// 아까 하나 더 만들었던 h2 DB를 적용할 수 있도록 위치를 지정해준다. 
class ItemRepositoryTest {

	// 의존성 주입 : autowired 이용하여 굳이 new 선언이나 setter을 사용하지 않아도 되게 해줌.
	@Autowired
	ItemRepository itemRepository;

	// 테스트 할 메소드 위에 @test를 붙여준다.
	// @Test
	// @DisplayName("상품 저장 테스트") // test코드에 이름을 넣어준다.
//	public void createItemTest() {
//		Item item = new Item();
//		item.setItemNm("테스트 상품");
//		item.setPrice(10000);
//		item.setItemDetail("테스트 상품 상세 설명");
//		item.setItemSellstatus(ItemSellstatus.SELL);
//		item.setStockNumber(100);
//		item.setRegTime(LocalDateTime.now()); //현재 시간 저장.
//		item.setUpdateTime(LocalDateTime.now());
//		
//		Item savedItem = itemRepository.save(item); //insert, update의 역할을 해주는 아이
//		// 인터페이스를 이용해서 (jpa repository에 다 있기 때문에) save를 따로 만들필요 없이 바로 이용해준다.
//		
//		System.out.println(savedItem.toString());
//	}

	public void createItemTest() {
		for (int i=1; i<=10; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellstatus(ItemSellstatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now()); // 현재 시간 저장.
			item.setUpdateTime(LocalDateTime.now());
			Item savedItem = itemRepository.save(item);
		}
	}

//	@Test
//	@DisplayName("상품명 조회 테스트")
	public void findByItemNmTest() {
		this.createItemTest(); //이 과정에서 item테이블에 10개가 insert가 됨.
		List<Item> itemList = itemRepository.findByItemNm("테스트 상품2");
		// findByItemNm, 정의만 해놓으면 알아서 다 가져와 준다. 
		// List<Item> findByItemNm(String itemNm);의 itemNm에 "테스트 상품 1"이 들어가게 되고.
		// 테스트 상품 1인경우 레코드가 1이게 됨. 이걸 list에 담아서 뽑아온다.
		for (Item item : itemList) {
			System.out.println(item.toString()); //롬복으로 찍어줬기 때문에 알아서 다 가져올 것이다.
		}
	}
	
	
//	@Test
//	@DisplayName("상품명 or 상품 상세설명 조회 테스트")
	public void findByItemNmOrItemDetail() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
//	@Test
//	@DisplayName("가격 LessThan 테스트")
	public void findByPriceLessThanTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByPriceLessThan(10005);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("가격 내림차순 조회 테스트")
	public void findByPriceLessThanOrderByPriceDescTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("이중찾기 테스트")
	public void findByItemNmAndItemSellstatus() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemNmAndItemSellstatus("테스트 상품1", ItemSellstatus.SELL);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
//	@Test
//	@DisplayName("between 테스트")
	public void findByPriceBetween() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByPriceBetween(10004, 10008);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}		
	}

	@Test
	@DisplayName("regTime 테스트")
	public void findByregTimeAfter() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByregTimeAfter(LocalDateTime.of(2023, 1, 1, 12, 12, 44));
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("Not 테스트")
	public void findByitemSellstatusIsNotNull() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByitemSellstatusIsNotNull();
		for (Item item : itemList) {
			System.out.println(item.toString());
		}	
	}
	
	//@Test
	//@DisplayName("엔딩 테스트")
	public void findByItemDetailEndingWith() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetailEndingWith("1");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}

	
}
