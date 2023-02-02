package com.trable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;


import com.trable.constant.UserGrade;
import com.trable.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {
	
	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserGrade usergrade;
	
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member(); // 생성자 member를 가져옴.
		
		member.setEmail(memberFormDto.getEmail());
		//ENCODING PASSWORD
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		member.setUsergrade(UserGrade.ONE);
			
		return member;
	}
}
