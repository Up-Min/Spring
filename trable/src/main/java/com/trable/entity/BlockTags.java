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
@Table(name = "blocktag")
@Getter
@Setter
@ToString
public class BlockTags {

	@Id
	@Column(name = "blocktag_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String blocktagname;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	public static BlockTags createBlockTag(Member member, String name) {
		BlockTags blockTag = new BlockTags();
		blockTag.setBlocktagname(name);
		blockTag.setMember(member);
		return blockTag;
	}
	
}

