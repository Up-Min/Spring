package com.myshop.entity;

import javax.persistence.*;

import org.hibernate.annotations.ManyToAny;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart_item") // 테이블 이름
@Getter
@Setter
@ToString
public class CartItem {
	@Id
	@Column (name="cart_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 부모테이블 2개를 가져올거임
	//cart_item, 자신을 기준으로 다:1이기 떄문에 many to one을 해준다.
	
	@ManyToOne (fetch = FetchType.LAZY) //지연로딩 활성화
	@JoinColumn (name = "cart_id")
	private Cart cart;
	
	@ManyToOne (fetch = FetchType.LAZY) //지연로딩 활성화	
	@JoinColumn (name = "item_id")
	private Item item;
	
	
	private int count;
	
	
	
}
