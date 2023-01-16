package com.myshop.entity;

import javax.persistence.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.myshop.constant.Role;
import com.myshop.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="member") //테이블 명을 설정.
@Getter
@Setter
@ToString
public class Member {
	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO) //Pk 생성 전략 : db에 맞춰서 알아서 해줌 (auto)
	private Long id;
	
	private String name;
	
	@Column(unique = true) //중복이 안되는 unique 컬럼 적용
	private String email;
	
	private String password;
	
	private String address;
	
	@Enumerated(EnumType.STRING) //문자 형태로 그 값 자체를 가져와준다
	private Role role;
	
	// member 엔티티를 생성하는 메소드 만들거다
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member(); // 생성자 member를 가져옴.
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setAddress(memberFormDto.getAddress());
		
		// password는 암호화가 되어야하기 때문에 한번 처리를 해줘야 한다.
		// 비밀번호 암호화
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		member.setRole(Role.USER);
		
		return member;
	}
}
