package com.self.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.self.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
	
}
