package com.trable.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table (name = "post")
@Getter
@Setter
@ToString
public class Post {

	@Id
	@Column(name="post_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String postname;
	
	private int like;
	
	@CreatedDate
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdate;
	
	@LastModifiedDate
	@Column(name = "modified_date")
	private LocalDateTime modifieddate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member memberid;
	
	@Column(name = "mainimg_ori")
	private String imgori;
	
	@Column(name = "mainimg_name")
	private String imgname;
	
	@Column(name = "mainimg_url")
	private String imgurl;

	
	
}
