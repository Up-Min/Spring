package com.trable.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trable.entity.Member;
import com.trable.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
		
	List<Post> findByMember(Member member);
	
	Optional<Post> findById(Long memberid);
}
