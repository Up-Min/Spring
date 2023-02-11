package com.trable.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trable.entity.Post;
import com.trable.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{
	
	
	Optional<Tag> findByTagname(String tagname);
	
	@Query(value = "select * from post_tag a join tag b on a.tag_id = b.tag_id where a.post_id = :postid", nativeQuery = true)
	List<Tag> findBypostid(@Param("postid") Long postid);
	
//	@Query(value = "select c.tagname from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where a.member_id = :MEMBERID group by c.tagname having count(c.tagname) >= '2'", nativeQuery = true)
//	List<Tag> getTagnamebycount (@Param("MEMBERID") Long memberid);

	@Query(value = "select c.tag_id, c.tagname from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where a.member_id = :MEMBERID", nativeQuery = true)
	List<Tag> getTagnamebycount (@Param("MEMBERID") Long memberid);
		
	
}
