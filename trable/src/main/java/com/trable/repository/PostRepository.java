package com.trable.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trable.constant.ShowPost;
import com.trable.entity.Member;
import com.trable.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
		
	List<Post> findByMember(Member member);
	
	Optional<Post> findById(Long memberid);
	
	@Query(value =  "select * from post i where i.show_post = 'show'",nativeQuery = true)
	List<Post> findByMemberNotshow(@Param("show_post") ShowPost showpost);
	
	
}
