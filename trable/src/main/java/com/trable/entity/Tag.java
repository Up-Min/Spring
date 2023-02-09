package com.trable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Tag")
@Getter
@Setter
@ToString
public class Tag {

	@Id
	@Column(name = "tag_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String tagname;
	
	public static Tag createTag(String name) {
		Tag tag = new Tag();
		tag.setTagname(name);
		return tag;
	}
	
}
