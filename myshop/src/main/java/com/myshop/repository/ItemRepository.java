package com.myshop.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myshop.constant.ItemSellstatus;
import com.myshop.entity.Item;


//엔티티 클래스에 있는 Item을 임포트 해옴.
//JpaRepository 상속을 받을건데 사용할 엔티티 클래스와,엔티티 클래스의 기본타입을 입력하면 된다. (Item 의 기본 타입은 Long였음)
//기본적인 CRUD처리를, 페이징 처리를 위한 메소드가 정의가 되어있음.
	// 지원하는 메소드는 ppt 참고

/* 아까 서비스에서 하나하나 팩토리를 만들어주고, try를 해서 일일이 만들고 했던거를 인터페이스를 이용해서 처리할 수 있다.
 * 
 */
public interface ItemRepository extends JpaRepository<Item, Long>{ 

	// 쿼리 메소드
	// 사용할 find 메소드에 대한 정의 필요함.
	List<Item> findByItemNm(String itemNm);
	// select * from item where item_nm = ? 와 동일함. itemNm으로 찾겠다는 의미임.
	// 나중에 ?에 들어갈 값은 메소드에서 매개변수로 받아낼거임.
	
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
	// select * from item where item_nm = ? or item_detail =?  동일
	
	List<Item> findByPriceLessThan(Integer price);
	// select * from item where price < ?
	
	List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
	// select * from item where price < ? order by price desc
	// 단점.. 너무 길어진다...! 그래서 준비해둔게 있다!!
	
	List<Item> findByItemNmAndItemSellstatus(String itemNm, ItemSellstatus itemSellStatus);
	
	List<Item> findByPriceBetween(Integer price, Integer price2);
	
	List<Item> findByregTimeAfter(LocalDateTime date);
	
	List<Item> findByitemSellstatusIsNotNull();
	
	List<Item> findByItemDetailEndingWith(String itemDetail);
	
	//JPQL 적용 - 딱히 이름에 규칙이 있는거는 아니다. 쿼리메소드와 다르게.
	
	@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc") 
	List<Item> findByItemDetail1(@Param("itemDetail") String itemDetail);
	// 어노테이션 @query (안에 쿼리문을 넣는다.from 에는 엔티티, 그리고 엔티티에'i'라는 별칭을 붙여준다.)
	// Item의 ItemDetail에서 ItemRepositoryTest에 있는 "테스트 상품 상세 설명+i"을 검색해주기 위해
	// 'String itemDetail' 값을 "itemDetail"에 적용하겠다는 @Param을 적용하게 하고
	// @Query문에 like %:itemDetail%에 적용될 수 있게 한다.
	// like 가 아닌 애들은 %안붙여도 된
	// 참고로 사용할 엔티티는 이름을 꼭 같이 맞춰줘야한다. 쿼리문도 엔티티에 저장되어있는 컬럼명을 맞춰줘야한다.
	
	// 파라미터를 다른 방법으로 주는 2번째 JPQL 방법
	@Query("select i from Item i where i.itemDetail like %?1% order by i.price desc") 
	List<Item> findByItemDetail(String itemDetail);
	// 파라미터를 따로 선언해서 넣어주는 것이 아니라 (@Param을 사용하는 것이 아니라) 
	// %?1% (첫번째 파라미터), %?2% (두번째 파라미터) ---- 이러한 순서로 사용하기도 한다.
	// 근데 위에서 배운 명시형 방법을 더 많이 쓰기는 한다.
	
	// 기존 db(오라클, mySQL등)에 사용하는 쿼리를 그대로 사용하는 nativeQuery 쓰는 방법!
	@Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
	List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
	// value 안에 쿼리문을 집어넣는다는 특징이 있음.
	// 쿼리문에 들어가는 컬럼명들은 엔티티에 있는거를 쓰는 것이 아니라, db실제 컬럼명을 넣어야 한다는 점...! 주의해라!
	// DB에서 컬럼을 확인하고 넣어야한다 잊지마라. 이 부분이 가장 큰 차이점이다.
	
	@Query("select i from Item i where i.price >= :Price")
	List<Item> findByprice(@Param("Price") int Price);
	
	@Query("select i from Item i where i.itemNm = :itemNm and i.itemSellstatus = :itemsellstatus")
	List<Item> findAndByQuery(@Param("itemNm") String itemNm, @Param("itemsellstatus") ItemSellstatus itemSellStatus);







}


