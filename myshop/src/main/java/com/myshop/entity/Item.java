package com.myshop.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.myshop.constant.ItemSellStatus;
import com.myshop.dto.ItemFormDto;
import com.myshop.exception.OutOfStockException;

import lombok.*;


@Entity
@Table(name="item") //테이블 명을 설정, 설정 안하면 클래스 명으로 해주기는 하는데 뭐,,, 강의 목적상...! ^^
@Getter
@Setter
@ToString
public class Item extends BaseEntity {
	@Id
	@Column (name = "item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // 상품코드
	// 왜 long가 아닌 Long로 했는가? int는 null값을 넣을 수 없지만, Long, String은 객체형태이기 때문에 null값을 집어넣을 수 있다.
	// 즉, int형 변수를 넣으려 하는데 notnull 없이 하고 싶다면, 필드타입 int가 아닌 객체 타입 integer형태로 넣어야한다.
	
	@Column (nullable = false, length = 50) //not null, 길이는 50으로 지정.
	private String itemNm; // 상품명
	
	@Column (nullable = false, name = "price") //not null, 컬럼 이름 지정.
	private int price; // 가격
	
	@Column (nullable = false)
	private int stockNumber; // 재고수량
	
	@Lob // 큰 데이터 값을 저장해주기 위해 사용
	@Column (nullable = false)
	private String itemDetail; // 상품 상세설명
	
	@Enumerated(EnumType.STRING) // enum타입 구성요소 문자 그 자체로 저장.
	private ItemSellStatus itemSellStatus; // 상품 판매상태
	
	public void updateItem(ItemFormDto itemFormDto) {
		this.itemNm = itemFormDto.getItemNm();
		this.price = itemFormDto.getPrice();
		this.stockNumber = itemFormDto.getStockNumber();
		this.itemDetail = itemFormDto.getItemDetail();
		this.itemSellStatus = itemFormDto.getItemSellStatus();
	}
	
	// 상품의 재고량 감소
	public void removeStock(int stockNumber) {
		int restStock = this.stockNumber-stockNumber; // 주문 후 남은 재고수량
				
		if(restStock < 0) {
			throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량 : "+ this.stockNumber + ")");
		}
		
		this.stockNumber = restStock; // 주문 후 남은 재고수량은 현재의 재고 값으로 할당
	}
	
	// 상품의 재고 증가
	public void addStock(int stockNumber) {
		this.stockNumber += stockNumber;
	}
}
