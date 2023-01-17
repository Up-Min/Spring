package com.myshop.dto;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

// 회원가입으로부터 넘어오는 가입정보를 담을 DTO
@Getter
@Setter

public class MemberFormDto {
	
	@NotBlank (message = "이름은 필수 입력 값 입니다.")
	private String name;
	
	@NotEmpty(message = "이메일은 필수 입력 값 입니다.")
//	null이나, 문자열의 길이 0인지 검사
	@Email(message = "이메일 형식으로 입력해주세요.")
// 이메일 형식을 가지고 있는지 검사 
	private String email;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값 입니다.")
	@Length(min = 8, max = 16, message = "비밀번호 는 8자 이상, 16자 이하로 입력해주새요.")
	private String password;
	
	@NotEmpty(message = "주소는 필수 입력 값 입니다.")
	private String address;
	
	//여기에 작성한 아이들은 memberform.html에 p태그에 field.hasErrors에 들어가게 된다.
}
