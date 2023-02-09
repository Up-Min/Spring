package com.trable.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trable.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{
	
	
	Optional<Tag> findByTagname(String tagname);
	
	@Query(value = "select * from post_tag a join tag b on a.tag_id = b.tag_id where a.post_id = :postid", nativeQuery = true)
	List<Tag> findBypostid(@Param("postid") Long postid);
	
}
