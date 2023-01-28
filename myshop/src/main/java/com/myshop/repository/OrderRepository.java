package com.myshop.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myshop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	// 추상메소드 작성 (현재 로그인한 사용자의 주문 데이터를 페이징 조건을 붙여서 가져옴)
	@Query("select o from Order o where o.member.email = :email order by orderDate desc")
	List<Order> findOrders(@Param("email") String email, Pageable pageable);
	// 매개변수 email를 쿼리문에 사용하기 위해 앞에 @param을 붙여줌. Order 엔티티 컬럼 기준으로 쿼리문 작성한다.
	
	
	// 현재 로그인한 회원의 주문 갯수가 몇개인지 조회
	@Query("select count(o) from Order o where o.member.email = :email")
	Long countOrder(@Param("email") String email);
		
	
		
}
