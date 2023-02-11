package com.trable.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.trable.constant.ShowPost;
import com.trable.dto.PostFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table (name = "post")
@Getter
@Setter
@ToString
public class Post extends BaseTimeEntity{

	@Id
	@Column(name="post_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String postname;
	
	@Column(nullable = false)
	private int heart;
	
	@Enumerated(EnumType.STRING)
	private ShowPost showPost; 
	
	@Column(name = "mainimg_ori", nullable = false)
	private String imgori;
	
	@Column(name = "mainimg_name", nullable = false)
	private String imgname;
	
	@Column(name = "mainimg_url", nullable = false)
	private String imgurl;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	
	public static Post createPost(Member member, PostFormDto postFormDto) {
		Post post = new Post();	
		post.setPostname(postFormDto.getPostname());
		post.setHeart(postFormDto.getHeart());
		post.setMember(member);
		post.setShowPost(ShowPost.SHOW);
		return post;
	}
	
	public void updatePost(Member member, PostFormDto postFormDto) {
		this.postname = postFormDto.getPostname();
		this.heart = postFormDto.getHeart();
		this.member = member;
	}
	
	public void updatePostHeart() {
		this.heart = this.heart+1;
	}
	
	public void updatePostShow() {
		this.showPost = ShowPost.SHOW;
	}
	
	public void updatePostHide() {
		this.showPost = ShowPost.HIDE;
	}
	
	public void updatePostImg(String imgori, String imgname, String imgurl) {
		this.imgori = imgori;
		this.imgname = imgname;
		this.imgurl = imgurl;
	}
	
	
}
