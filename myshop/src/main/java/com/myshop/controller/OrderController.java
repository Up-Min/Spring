package com.myshop.controller;


import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myshop.dto.OrderDto;
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
}
