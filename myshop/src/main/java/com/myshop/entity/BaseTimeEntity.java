package com.myshop.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.*;

@EntityListeners(value = {AuditingEntityListener.class}) //Auditing을 적용하기 위해 쓴다.
@MappedSuperclass // 부모클래스를 상속받는 자식클래스한테 Mapping정보만 제공한다. (나중에 상속해 줄 것을 대비하여!)
@Getter
@Setter
public class BaseTimeEntity {
	
	@CreatedDate //엔티티가 생성되서 저장될 때 시간을 자동으로 저장
	@Column(updatable = false) //updatable = false : 등록날짜에 대한 수정이 불가능하게 함.
	private LocalDateTime regTime; // 등록일
	
	@LastModifiedDate
	private LocalDateTime upDateTime; // 수정일
	
}
