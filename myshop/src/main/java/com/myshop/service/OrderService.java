package com.myshop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.dto.OrderDto;
import com.myshop.entity.Item;
import com.myshop.entity.Member;
import com.myshop.entity.Order;
import com.myshop.entity.OrderItem;
import com.myshop.repository.ItemImgRepository;
import com.myshop.repository.ItemRepository;
import com.myshop.repository.MemberRepository;
import com.myshop.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;
	
	public Long order(OrderDto orderDto, String email) {
		// item table에 대한 item_id를 가져온다. (주문할 상품)
		Item item = itemRepository.findById(orderDto.getItemId())
					.orElseThrow(EntityNotFoundException::new);
		
		// 매개변수 email을 이용하여 member table에서 회원정보를 가져온다.
		Member member = memberRepository.findByEmail(email);
		
		// 주문 정보 담을 List 선
		List<OrderItem> orderItemList = new ArrayList<>();
		
		// 주문할 상품과, orderDto에서 받아온 주문량을 OrderItem에 요청한다.
		OrderItem orderitem = OrderItem.createOrderItem(item, orderDto.getCount());
		
		// List에 item 추가 (item, orderitem 종료)
		orderItemList.add(orderitem);
		
		//Order과 Member 이 연관관계이기 떄문에, member에 관한것도 order에 넣어준다.
		Order order = Order.createOrder(member, orderItemList);
		
		// 데이터 저장
		orderRepository.save(order);
		
		return order.getId();
	}
	
	
}
