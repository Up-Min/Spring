package com.trable.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trable.entity.Post;
import com.trable.entity.PostTag;

public interface PostTagRepository extends JpaRepository<PostTag, Long>{

	@Query(value = "delete from post_tag a where a.post_id =:postid",nativeQuery = true)
	void deleteposttagbypostid(@Param("postid") Long postid);
	
	@Query(value = "select * from post_tag a where a.post_id = :postid", nativeQuery = true)
	List<PostTag> getpostlistbyid(@Param("postid") Long postid);
	
	PostTag findBypost(Long postid);
	
}
