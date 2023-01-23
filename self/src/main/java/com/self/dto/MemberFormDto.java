package com.self.dto;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {

	@NotBlank (message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@NotEmpty (message = "이메일은 필수 입력 값입니다.")
	@Email (message = "올바른 이메일 형태로 입력해주세요.")
	private String email;
	
	@NotEmpty (message = "비밀번호는 필수 입력 값입니다.")
	@Length (min = 8, max = 16, message = "8~16자 사이로 입력해주세요.")
	private String password;
	
	@NotEmpty (message = "주소는 필수 입력 값입니다.")
	private String address;
		
}
