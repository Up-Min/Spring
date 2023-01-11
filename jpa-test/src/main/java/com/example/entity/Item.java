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

public class Item {
	
	@Id 
	@Column (name = "item_id")
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (nullable = false)
	private String name;
	
	@Column (nullable = false)
	private int price;
	 
	@Column (nullable = false)
	private int stockQuantity;
	
}
