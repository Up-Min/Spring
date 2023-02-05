package com.trable.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import com.trable.constant.UserGrade;
import com.trable.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
	
		@NotBlank(message = "이메일은 필수 입력 값입니다.")
		private String email;
		
		@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
		@Length(min =4, max=16, message = "비밀번호는 4자 이상 16자 이하로 입력 바랍니다.")
		private String password;
		
		private UserGrade userGrade;
		
		private String imgname;
		
		private String imgurl;
		
		private String imgori;
		
		private static ModelMapper modelMapper = new ModelMapper();
		
		public Member createMember() {
			return modelMapper.map(this, Member.class);
		}
		
}
