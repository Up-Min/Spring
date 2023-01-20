package com.myshop.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import com.myshop.constant.ItemSellStatus;
import com.myshop.entity.Item;
import com.myshop.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@SpringBootTest // test에는 항상 넣어줘야한다.test class로 쓰겠다는 의미!
@TestPropertySource(locations = "classpath:application-test.properties")
// 아까 하나 더 만들었던 h2 DB를 적용할 수 있도록 위치를 지정해준다. 
class ItemRepositoryTest {

	// 의존성 주입 : autowired 이용하여 굳이 new 선언이나 setter을 사용하지 않아도 되게 해줌.
	@Autowired
	ItemRepository itemRepository;
	
	
	//Querydsl 사용하기
	// 영속성 컨텍스트를 가져와야 한다 - 애를 쓰기 위해 엔티티 매니저도 가져와야한다.
	@PersistenceContext //영속성 컨텍스트 호출
	EntityManager em; //엔티티 매니저 호출
	

	public void createItemTest() {
		for (int i=1; i<=10; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now()); // 현재 시간 저장.
//			item.setUpdateTime(LocalDateTime.now());
			Item savedItem = itemRepository.save(item);
		}
	}
	
	public void createItemTest2() {
		for (int i=1; i<=5; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now()); // 현재 시간 저장.
//			item.setUpdateTime(LocalDateTime.now());
			Item savedItem = itemRepository.save(item);
		}
		for (int i=6; i<=10; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
			item.setStockNumber(0);
			item.setRegTime(LocalDateTime.now()); // 현재 시간 저장.
//			item.setUpdateTime(LocalDateTime.now());
			Item savedItem = itemRepository.save(item);
		}
	}


	

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
	//@DisplayName("문제 1")
	public void findByItemNmAndItemSellstatus() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemNmAndItemSellStatus("테스트 상품1", ItemSellStatus.SELL);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
//	@Test
//	@DisplayName("문제 2")
	public void findByPriceBetween() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByPriceBetween(10004, 10008);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}		
	}

	//@Test
	//@DisplayName("문제 3")
	public void findByregTimeAfter() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByregTimeAfter(LocalDateTime.of(2023, 1, 1, 12, 12, 44));
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("문제 4")
	public void findByitemSellstatusIsNotNull() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByitemSellStatusIsNotNull();
		for (Item item : itemList) {
			System.out.println(item.toString());
		}	
	}
	
	//@Test
	//@DisplayName("문제 5")
	public void findByItemDetailEndingWith() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetailEndingWith("1");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}

	//JPQL 실행
	
	//@Test
	//@DisplayName("@Query Select Test")
	public void findByItemDetailTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("@NativeQuery Select Test")
	public void findByItemDetailByNativeTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("문제 1")
	public void findByprice() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByprice(10005);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("문제 2")
	public void findAndByQuery() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findAndByQuery("테스트 상품1", ItemSellStatus.SELL);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	
	// 쿼리문 선언
	//@Test
	//@DisplayName("Querydsl 조회 테스트")
	public void queryDslTest() {
		this.createItemTest();
		JPAQueryFactory qf = new JPAQueryFactory(em); 
		// 쿼리를 동적으로 생산하기 위한 JPAQueryFactory -> 앞서 선언한 entity mananger을 통해 처리하려면 얘가 있어야한다.
		
		QItem qItem = QItem.item; 
		// Querydsl 환경만들면서 자동으로 생성한 QItem.java를 가져온다.
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
							.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
							.where(qItem.itemDetail.like("%테스트 상품 상세 설명%"))
							.orderBy(qItem.price.desc());
		// 우리가Item 엔티티를 사용하고 있기 때문에 <Item>을 넣어준다.
		// selectFrom이 DB에서 select from과 동일하다.
		// select * from item 과 동일해짐
		// 그리고 그 이후에는 체이닝 '.'을 붙여서 계속해서 이어나갈 수 있게 해준다.
		// .where(qItem.itemSellstatus.eq(ItemSellstatus.SELL))
		// = where itemSellstatus = 'SELL'과 동일해진다.
		// .where(qItem.itemDetail.like("%테스트 상품 상세 설명%"));
		// = where itemDetail like %테스트 상품 상세 설명% 과 동일해진다.
		// .orderBy(qItem.price.desc()); = 가격 내림차순
		
	 // 쿼리문 실행 - ppt JPAQuery 반환 메소드 참고
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
		// 페이징 하는거 만들기 (검색기능)
		@Test
		@DisplayName("Querydsl 조회 테스트2")
		public void queryDslTest2() {
			this.createItemTest2();
			Pageable page = PageRequest.of(0, 5);
			// 게시판 페이지를 생각한다. of의 첫번째 변수는 조회할 페이지의 번호이고, 두번째 변수는 한 페이지당 조회할 데이터의 갯수이다.
			JPAQueryFactory qf = new JPAQueryFactory(em); 
			QItem qItem = QItem.item; 
			
			JPAQuery<Item> query = qf.selectFrom(qItem)
					.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
					.where(qItem.itemDetail.like("%테스트 상품 상세 설명%"))
					.where(qItem.price.gt(10003))
					.offset(page.getOffset()) //페이지에 대한 정보를 셋팅해준다.
					.limit(page.getPageSize()); // 데이터를 페이징 해서 가져올 수 있다.

			// 쿼리문 실행 - ppt JPAQuery 반환 메소드 참고
			List<Item> itemList = query.fetch();

			for (Item item : itemList) {
				System.out.println(item.toString());
			}			
		}
		
		//@Test
		//@DisplayName("문제3-1")
		public void QueryQuestion1() {
			this.createItemTest();
			JPAQueryFactory qf = new JPAQueryFactory(em);
			QItem qitem = QItem.item;
			
			JPAQuery<Item> query = qf.selectFrom(qitem)
					.where(qitem.itemNm.eq("테스트 상품1"))
					.where(qitem.itemSellStatus.eq(ItemSellStatus.SELL));
			
			List<Item> itemList = query.fetch();
			for (Item item : itemList) {
				System.out.println(item.toString());
			}	
		}
		
		//@Test
		//@DisplayName("문제3-2")
		public void QueryQuestion2() {
			this.createItemTest();
			JPAQueryFactory qf = new JPAQueryFactory(em);
			QItem qitem = QItem.item;
			
			JPAQuery<Item> query = qf.selectFrom(qitem)
					.where(qitem.price.between(10004, 10008));
			List<Item> itemList = query.fetch();
			for (Item item : itemList) {
				System.out.println(item.toString());
			}	
		}
		
		//@Test
		//@DisplayName("문제3-3")
		public void QueryQuestion3() {
			this.createItemTest();
			JPAQueryFactory qf = new JPAQueryFactory(em);
			QItem qitem = QItem.item;
			
			JPAQuery<Item> query = qf.selectFrom(qitem)
					.where(qitem.regTime.after(LocalDateTime.of(2023, 1, 1, 12, 12, 44)));
			List<Item> itemList = query.fetch();
			for (Item item : itemList) {
				System.out.println(item.toString());
			}	
		}
		
		//@Test
		//@DisplayName("문제3-4")
		public void QueryQuestion4() {
			this.createItemTest();
			JPAQueryFactory qf = new JPAQueryFactory(em);
			QItem qitem = QItem.item;
			
			JPAQuery<Item> query = qf.selectFrom(qitem)
					.where(qitem.itemSellStatus.isNotNull());
			List<Item> itemList = query.fetch();
			for (Item item : itemList) {
				System.out.println(item.toString());
			}	
		}
		
		//@Test
		//@DisplayName("문제3-5")
		public void QueryQuestion5() {
			this.createItemTest();
			JPAQueryFactory qf = new JPAQueryFactory(em);
			QItem qitem = QItem.item;
			
			JPAQuery<Item> query = qf.selectFrom(qitem)
					.where(qitem.itemDetail.endsWith("설명1"));
			List<Item> itemList = query.fetch();
			for (Item item : itemList) {
				System.out.println(item.toString());
			}	
		}	
}
