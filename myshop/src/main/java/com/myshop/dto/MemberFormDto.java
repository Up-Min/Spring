package com.myshop.dto;

import lombok.Getter;
import lombok.Setter;

// 회원가입으로부터 넘어오는 가입정보를 담을 DTO
@Getter
@Setter

public class MemberFormDto {
	private String name;
	private String email;
	private String password;
	private String address;
}
