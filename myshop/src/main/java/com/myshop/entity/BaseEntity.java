package com.myshop.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@EntityListeners(value = {AuditingEntityListener.class}) 
@MappedSuperclass 
@Getter
@Setter
public class BaseEntity extends BaseTimeEntity{
	
	@CreatedBy // spring에서 등록자임을 인식시킴.
	@Column (updatable = false)
	private String createdBy; // 등록자
	
	@LastModifiedBy // spring에서 수정자임을 인식시킴.
	private String modefiedBy; // 수정자
	
}
