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
@Table(name = "blockmember")
@Getter
@Setter
@ToString
public class BlockMembers {

	@Id
	@Column(name = "blockmember_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String blockmembername;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	
	public static BlockMembers createBlockMember(Member member, String name) {
		BlockMembers blockmember = new BlockMembers();
		blockmember.setBlockmembername(name);
		blockmember.setMember(member);
		return blockmember;
	}
	
}
