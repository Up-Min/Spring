package com.myshop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.myshop.dto.OrderDto;
import com.myshop.dto.OrderHistDto;
import com.myshop.dto.OrderItemDto;
import com.myshop.entity.Item;
import com.myshop.entity.ItemImg;
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
public class OrderService2 {

	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;
	private final ItemImgRepository itemImgRepository;
	
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
	
	// 상품정보 + 상품이미지 페이징 가공
	@Transactional(readOnly = true)
	public Page<OrderHistDto> getOrderList(String email, Pageable pageable){
		
		// orderRepository에서 가져온 상품 정보 리스트에 저장.
		List<Order> orders = orderRepository.findOrders(email, pageable);
		
		// 페이징 처리를 위해 orderRepository에서 상품 count 불러옴. (총 주문량)
		Long totalCount = orderRepository.countOrder(email);
		
		// 주문 목록을 담을 List 선언. 
		List<OrderHistDto> orderHistDtos = new ArrayList<>();
		
		for (Order order : orders) {
			// OrderHistDto에 있는 생성자를 이용해 넘어온 order의 정보들을 적용한다.
			OrderHistDto orderHistDto = new OrderHistDto(order);
			List<OrderItem> orderItems = order.getOrderItems();
			
			for(OrderItem orderItem : orderItems) {
				// 주문 목록에서 대표 이미지를 보여줄 거기 때문에, 이미지를 가져와야함.
				ItemImg itemImg = itemImgRepository
						.findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");
				// OrderItemDto 생성자에 item과 imgURL을 가져온다.
				OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
				// OrderItemDto를 orderHistDto에 add 해준다.
				orderHistDto.addOrderItemDto(orderItemDto);
			}
		// for 문 이후 list에 orderHistDto들을 넣어준다.
			orderHistDtos.add(orderHistDto);
		}
		
		// 페이징 처리이기 떄문에 페이징 객체로 리턴한다.
		return new PageImpl<OrderHistDto>(orderHistDtos,pageable,totalCount);
	}
	
	// 현재 로그인한 사용자 == (취소)주문 요청한 사용자? 검증 필요. 통과하면 주문 status 변경.
	
	// 사용자 검증
	public boolean ValidateOrder(Long orderId, String email) {
		Member curMember = memberRepository.findByEmail(email); //로그인 한 사용자 찾기
		Order order = orderRepository.findById(orderId)
					 .orElseThrow(EntityNotFoundException::new);
		Member savedMember = order.getMember(); //주문한 사용자 찾기
		
		// 사용자 비교
		if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
			return false;
		}
		return true;
	}
	
	// 주문 취소
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
					  .orElseThrow(EntityNotFoundException::new);
		order.cancelOrder();
	}
	
	// 주문 삭제
	public void deleteOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(EntityNotFoundException::new);
		
		// JPA Repository가 extend된 orderRepository의 delete 사용.
		orderRepository.delete(order);
	}
	
}
