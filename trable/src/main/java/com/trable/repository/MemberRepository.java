package com.trable.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trable.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findByEmail(String Email);
	
	Optional<Member> findById(Long id);
	
}
