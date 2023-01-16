package com.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myshop.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	// find by 메소드 이용해서 값을 가져와볼게용
	Member findByEmail(String email); // 회원가입시 이메일 중복이 있는지 검사하기 위함.
	

	
	
}
