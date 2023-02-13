package com.trable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trable.entity.BlockMembers;
import com.trable.entity.BlockTags;

public interface BlockTagsRepository extends JpaRepository<BlockTags, Long>{

	@Query(value = "select * from blocktag where blocktag.member_id = :NUM", nativeQuery = true)
	List<BlockTags> findbymemid(@Param("NUM") Long memberid);
	
	@Query(value = "delete from blocktag where blocktag.member_id = :MEMID", nativeQuery = true)
	void delbtbymemid(@Param("MEMID") Long memid);
	
}
