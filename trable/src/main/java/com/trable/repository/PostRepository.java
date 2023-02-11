package com.trable.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trable.constant.ShowPost;
import com.trable.entity.Member;
import com.trable.entity.Post;
import com.trable.entity.Tag;

public interface PostRepository extends JpaRepository<Post, Long> {
		
	List<Post> findByMember(Member member);
	
	Optional<Post> findById(Long memberid);
	
	@Query(value =  "select * from post i where i.show_post = 'show'",nativeQuery = true)
	List<Post> findByMemberNotshow(@Param("show_post") ShowPost showpost);
	
	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG",nativeQuery = true)
	List<Post> findByTag(@Param("TAG") Tag tag);

	@Query(value = "select * from post a join post_tag b on a.post_id = b.post_id join tag c on b.tag_id = c.tag_id where c.tagname = :TAG",nativeQuery = true)
	List<Post> findByTag1(@Param("TAG") String tag);



	
	
}
