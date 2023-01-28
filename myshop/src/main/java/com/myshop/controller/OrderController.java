package com.myshop.controller;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myshop.dto.OrderDto;
import com.myshop.dto.OrderHistDto;
import com.myshop.entity.Order;
import com.myshop.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;
	
	
	
	@PostMapping (value = "/order")
	// 비동기 처리시 사용할 annotation 
	// itemDtl에서 넘어온 비동기에 대해 controller에서 처리할거임.
	public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto, 
					BindingResult bindingResult, Principal principal ) {
		/* 로그인한 사용자의 정보를 가져올 수 있는 Principal.
		 * @RequestBody : http 본문에 있는 객체를 java로 넘겨주는 역할을 함.
		 * 비동기 요청이 왔을 때 'body' 에 있는 데이터라 생각하고 그대로 orderDto에 넣어준다.
		 * @ResponseBody : 자바 객체를 넘겨주는 역할.  
		 * ResponseEntity : Header, Body를 포함하는 클래스. 넘겨받은 값을 Header, Body에 담아서, 보내준다.
		 * http request, response의 Header, Body에 대한 이해 필요.
		*/
		
		// 비동기 에러처리
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			/* fleldError도 validation에 포함된다.
			* orderDto에 대해 에러가 발생했을 때, 에러를 그대로 List에 넣어줄 수 있게 한다.
			*  bindingResult.getFieldErrors()는 List<FieldError>의 형태를 갖는다.
			*/
			
			for(FieldError fieldError : fieldErrors) {
				// 에러메세지 문자열을 넣어줄거임.
				sb.append(fieldError.getDefaultMessage());
			}
			
			// response Entity를 통해 에러메세지 전송
			return new ResponseEntity<String>(sb.toString(),HttpStatus.BAD_REQUEST);	
		}
		
		// username parameter을 email로 지정했기 때문에, email이 올 예정
		String email = principal.getName();
		Long orderId;
		
		try {
			// orderservice 호출, 이미 관계성을 order 엔티티로 다 해줬기 때문에,
			// orderservice에서 orderRepository.save(order); 이거만 하면 다 된다.
			orderId = orderService.order(orderDto, email);
		} catch (Exception e) {
			// 예외 발생시 에러메세지 전송
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);	
		}
		
		// orderId에 맞게 <Long>으로 변환 후 성공메세지 responseEntity로 리턴함.
		return new ResponseEntity<Long>(orderId,HttpStatus.OK);	
	
	}
	
	@GetMapping(value = {"orders","/orders/{page}"})
	// {page}로 받은 파라미터를 @PathVariable로 받아온다. 
	public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
		
		// ItemController과 동일한 pageable 선언.
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3); 
		
		Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(principal.getName(), pageable);
		
		//view단으로 보내기
		model.addAttribute("orders", orderHistDtoList);
		model.addAttribute("page", pageable.getPageNumber());
		model.addAttribute("maxPage", 5);
		
		return "order/orderHist";
	}
	
	@PostMapping(value = "/order/{orderId}/cancel")
	public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId, Principal principal ) {
		//OrderService에서 선언한 사용자 검증 우선 작동
		if(!orderService.validateOrder(orderId, principal.getName())){
			return new ResponseEntity<String>("주문 취소 권한이 없습니다.",HttpStatus.FORBIDDEN);
		}
		// OrderService의 cancelOrder 호출
		orderService.cancelOrder(orderId);
		
		// 정상적일시 200코드 반환
		return new ResponseEntity<Long>(orderId, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/order/{orderId}/delete")
	public @ResponseBody ResponseEntity deleteOrder(@PathVariable("orderId") Long orderId, Principal principal ) {
		if(!orderService.validateOrder(orderId, principal.getName())){
			return new ResponseEntity<String>("주문 취소 권한이 없습니다.",HttpStatus.FORBIDDEN);
		}
		
		orderService.deleteOrder(orderId);
		return new ResponseEntity<Long>(orderId, HttpStatus.OK);
	}
	
}	
