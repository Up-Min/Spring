package com.trable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table (name = "postimg")
@Getter
@Setter
@ToString
public class PostImg {
	
	@Id
	@Column(name="postimg_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "img_name")
	private String imgname;
	
	@Column(name = "img_url")
	private String imgurl;
	
	@Column(name = "img_ori")
	private String imgori;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;
	
	
	public void updateImg(String imgori, String imgname, String imgurl) {
		this.imgori = imgori;
		this.imgname = imgname;
		this.imgurl = imgurl;
	}
}
