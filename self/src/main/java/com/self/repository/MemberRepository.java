package com.self.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.self.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Member findByEmail(String email);

}
