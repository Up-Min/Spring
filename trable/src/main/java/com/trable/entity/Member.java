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

	@Column(name = "img_ori")
	private String imgori;
	
	@Column(name = "img_name")
	private String imgname;
	
	@Column(name = "img_url")
	private String imgurl;

	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member(); // 생성자 member를 가져옴.
		
		member.setEmail(memberFormDto.getEmail());
		//ENCODING PASSWORD
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		member.setUsergrade(UserGrade.ONE);
		
		return member;
	}
	
	public void updateImg(String imgori, String imgname, String imgurl) {
		this.imgori = imgori;
		this.imgname = imgname;
		this.imgurl = imgurl;
	}
	
	public void updatepwd(String pwd, PasswordEncoder passwordEncoder) {
		String password = passwordEncoder.encode(pwd);
		this.password = password;
	}
	
	public void updategrade(UserGrade userg) {
		this.usergrade = userg;
	}
	
}
