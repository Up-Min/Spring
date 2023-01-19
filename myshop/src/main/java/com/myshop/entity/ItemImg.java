package com.myshop.entity;

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

import com.myshop.constant.ItemSellstatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="item_img") 
@Getter
@Setter
public class ItemImg extends BaseEntity{

	@Id
	@Column (name="item_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String imgName; // 이미지 파일명
	
	private String oriImgName; // 원본 이미지 파일명
	
	private String imgUrl; // 이미지 조회 경로
	
	private String repimgYn; // 대표 이미지 여부
	
	@ManyToOne (fetch = FetchType.LAZY) // 지연로딩
	@JoinColumn(name = "item_id")
	private Item item;
	
	//원본 이미지 파일명, 업데이트 할 이미지 파일명, 이미지 경로를 파라메터로 받아서 이미지 정보를 업데이트 함.
	public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
		this.oriImgName = oriImgName;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
	}
	
	
}
