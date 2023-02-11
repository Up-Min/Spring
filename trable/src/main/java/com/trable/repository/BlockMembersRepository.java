package com.trable.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trable.entity.BlockMembers;

public interface BlockMembersRepository extends JpaRepository<BlockMembers, Long>{

	@Query(value = "select * from blockmember where blockmember.member_id = :MEMBERNUM", nativeQuery = true)
	List<BlockMembers> findbmbymemid(@Param("MEMBERNUM") Long memberid);
	
}
