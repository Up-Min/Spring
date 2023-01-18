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
public class OrderItem {

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
	
	private int order_price; //주문 가격
	
	private int count; //주문수량
	
}
