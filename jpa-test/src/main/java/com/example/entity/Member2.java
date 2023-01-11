package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
public class Member2 {

	@Id
	@Column (name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (nullable = false)
	private String name;
	
	@Column (nullable = false)
	private String city;
	
	@Column
	private String street;
	
	@Column
	private String zipcode;
	
}
