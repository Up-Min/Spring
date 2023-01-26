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
	
	
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem); // orderItem 객체를 List에 넣어준다.
		orderItem.setOrder(this); 
		// order과 orderItem이 양방향 매핑이었기 때문에, OrderItem 객체에도 Order객체를 셋팅 해줘야한다?
		// 서로가 서로를 참조하는 양방향 매핑. 
			
	}
	
	//order 객체를 생성해주는 createOrder.
	public static Order createOrder(Member member, List<OrderItem> orderItemList) {
		Order order = new Order();
		order.setMember(member); 
		// order에 member테이블이 연관성이 있었기 떄문에, member 값도 order에 넣어준다.
		
		for (OrderItem orderItem : orderItemList) {
			order.addOrderItem(orderItem);
		}
		
		// 주문 상태 변경
		order.setOrderStatus(OrderStatus.ORDER);
		// 주문 날짜 지정
		order.setOrderDate(LocalDateTime.now());
		
		return order;
	}
	
	// 총 주문금액
	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
	}
}
