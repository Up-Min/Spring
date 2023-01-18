package com.myshop.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.myshop.constant.OrderStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="orders") // 테이블 이름
@Getter
@Setter
@ToString
public class Order {
	@Id
	@Column (name = "order_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne (fetch = FetchType.LAZY) //지연로딩 활성화
	@JoinColumn (name = "member_id")
	private Member member;
	
	private LocalDateTime orderDate; // 주문일
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus; // 주문상태
	
	// 다방향 매핑 선언
	// OrderItem이 여러개이기 때문에, list를 만들어 줘야함.
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) 
	//OrderItem에 있는 order에 의해 관리가 된다는 의미. / 
	// cascade : 영속성 전이 - order엔티티가 부모이기 때문에 order @OneToMany에 넣는다.
	// type가 All이기 때문에 cascade의 모든 속성을 적용시킨다.
	private List<OrderItem> orderItems = new ArrayList<>();
	
	
}
