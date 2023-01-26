package com.myshop.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.constant.ItemSellStatus;
import com.myshop.repository.CartRepository;
import com.myshop.repository.ItemRepository;
import com.myshop.repository.MemberRepository;
import com.myshop.repository.OrderItemRepository;
import com.myshop.repository.OrderRepository;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")

class OrderTest {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	@PersistenceContext
	EntityManager em;
	// 영속성 컨텍스트에 엔티티 저장. 엔티티 매니저를 통해 엔티티에 접근.

	public Item createItemTest() {
		Item item = new Item();
		item.setItemNm("테스트 상품");
		item.setPrice(10000);
		item.setItemDetail("테스트 상품 상세 설명");
		item.setItemSellStatus(ItemSellStatus.SELL);
		item.setStockNumber(100);
		item.setRegTime(LocalDateTime.now()); // 현재 시간 저장.
//		item.setUpdateTime(LocalDateTime.now());
		// Item savedItem = itemRepository.save(item); //save 여기서 안할거임.

		return item;
	}

	@Test
	@DisplayName("영속성 전이 테스트")
	public void cascadeTest() {
		Order order = new Order();
		for (int i = 0; i < 3; i++) {
			Item item = this.createItemTest(); // 아이템 3개 생성
			itemRepository.save(item); // 물건이 있어야 주문 할 수 있으니, 아이템을 넣어준다.

			OrderItem orderItem = new OrderItem();
			orderItem.setItem(item);
			orderItem.setCount(10);
			orderItem.setOrderprice(1000);
			orderItem.setOrder(order);
			// 생성한 물건을 orderItem에 넣어준다. 즉, 주문한 물건이 된다.

			order.getOrderItems().add(orderItem);
			//order에 생성되어있는 list getOrderItems에 앞서 만든 orderItem을 저장시켜준다.
			// 객체만 추가시켜주면, spring이 알아서 영속성 전이를 통해 order에 들어가는 것을 orderItem에도 들어가게 한다.
		}
		orderRepository.saveAndFlush(order); // 영속성 컨텍스트에 save에 하면서 flush를 통해 DB에도 반영시킴
		em.clear(); //영속성 컨텍스트 초기화.
		
		Order saveOrder = orderRepository.findById(order.getId()) //주문 테이블에 있는 order_id를 가져와서 찾는다.
						  .orElseThrow(EntityNotFoundException::new); //엔티티가 확인되지 않을 경우 에러를 throw 한다.
		
		assertEquals(3, saveOrder.getOrderItems().size()); //3개의 리스트를 orderItem에 저장했다. 3개가 정상적으로 저장됐는지 3과 비교한다.
		
				
	}
	

	public Order createOrder() {
		Order order = new Order();
		for (int i = 0; i < 3; i++) {
			Item item = this.createItemTest(); 
			itemRepository.save(item);

			OrderItem orderItem = new OrderItem();
			orderItem.setItem(item);
			orderItem.setCount(10);
			orderItem.setOrderprice(1000);
			orderItem.setOrder(order);

			order.getOrderItems().add(orderItem);

		} // 회원 한명이 3개의 물건을 주문 한 셈!
		
		 Member member = new Member();
		 memberRepository.save(member); // member 저장
		 order.setMember(member);
		 orderRepository.save(order); //  order 저장
		 
		 return order;
	}
	
	@Test
	@DisplayName("고아객체 제거 테스트")
	public void orphanRemovalTest() {
		Order order = this.createOrder();
		order.getOrderItems().remove(0);
		// Order엔티티(부모) 에서 OrderItem엔티티(자식)를 삭제했을 때 OrderItem엔티티가 삭제가 된다.
		em.flush();
	}
	
	
	@Test
	@DisplayName("지연 로딩 테스트")
	public void lazyLoadingTest() {
		Order order = this.createOrder(); 
		// 물건 3개를 만들고, 영속성 컨텍스트 member, order을 저장하고
		Long orderItemId = order.getOrderItems().get(0).getId();
		// 주문한 물건 중에서 첫번째 물건의 Id값을 가져온다. -> order_item table의 id를 구한다.
		
		em.flush();
		em.clear();
		
		// 가져오기
		OrderItem orderItem = orderItemRepository.findById(orderItemId) 
				//Long orderItemId 에서 가져온 id로 select 구문을 돌릴거임.
				.orElseThrow(EntityNotFoundException::new);
		
	}
}






