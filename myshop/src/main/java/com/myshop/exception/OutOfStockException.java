package com.myshop.exception;

public class OutOfStockException extends RuntimeException {
	// 재고가 품절시 발생하는 exception
	public OutOfStockException(String message) {
		super(message);
	}

}
