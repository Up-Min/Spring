package com.example.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class OrderItem {
	
	@Id
	@Column (name = "order_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (name = "order_id", nullable = false)
	private Long orderId;
	
	@Column (name = "item_id", nullable = false)
	private Long itemId;
	
	@Column (nullable = false)
	private int orderPrice;
	
	@Column (nullable = false)
	private int count;
	
	
}
