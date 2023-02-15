package com.trable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trable.entity.PostTag;

public interface PostTagRepository extends JpaRepository<PostTag, Long>{

	@Query(value = "delete from post_tag a where a.post_id =:postid",nativeQuery = true)
	void deleteposttagbypostid(@Param("postid") Long postid);
	
	
}
