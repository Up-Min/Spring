package com.myshop.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.myshop.constant.OrderStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="order_item") // 테이블 이름
@Getter
@Setter
@ToString
public class OrderItem extends BaseEntity {

	@Id
	@Column (name = "order_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne (fetch = FetchType.LAZY) //지연로딩 활성화
	@JoinColumn (name = "item_id")
	private Item item;
	
	@ManyToOne (fetch = FetchType.LAZY) //지연로딩 활성화
	@JoinColumn (name = "order_id")
	private Order order;
	
	private int orderprice; //주문 가격
	
	private int count; //주문수량
	
	// 주문할 상품과 주문 수량을 통해 orderItem객체를 만듬
	public static OrderItem createOrderItem (Item item, int count) {
		OrderItem orderItem = new OrderItem();
		
		orderItem.setItem(item);
		orderItem.setCount(count);
		orderItem.setOrderprice(count);
		
		item.removeStock(count); //주문량 만큼 재고를 없앰.
		
		return orderItem;
		
	}
	
	// 주문한 총 가격 return
	public int getTotalPrice() {
		return orderprice * count;
	}
	
}
