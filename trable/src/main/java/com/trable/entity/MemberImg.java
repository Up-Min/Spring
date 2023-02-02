package com.trable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "memberimg") 
public class MemberImg {
	
	@Id
	@Column(name = "memberimg_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String img_name;
	
	private String img_url;
	
	private String img_ori;
	
	// ONE TO ONE WITH MEMBER DTO
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	//UPDATE USER IMAGE
	public void updateMemberImg(String imgname, String imgurl, String imgori) {
		this.img_name = imgname;
		this.img_url = imgurl;
		this.img_ori = imgori;
				
	}
	
}
