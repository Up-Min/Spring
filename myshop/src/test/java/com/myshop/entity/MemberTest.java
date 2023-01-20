package com.myshop.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.repository.MemberRepository;

@SpringBootTest
@Transactional // 코드가 끝나고 롤백시킴.
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberTest {
	
	@Autowired
	MemberRepository memberRepository;

	@PersistenceContext
	EntityManager em;
	
	@Test
	@DisplayName("auditing 테스트")
	@WithMockUser(username = "gildong", roles = "USER") //가상의 회원을 만든다.
	public void auditingTest() {
		Member newMember = new Member();
		memberRepository.save(newMember); // 영속성컨텍스트 저장
		
		em.flush(); //DB반영
		em.clear(); //엔티티메니저 클리어 - 엔티티메니저가 없이서 DB에서 가져오게 된다.
		
		Member member = memberRepository.findById(newMember.getId())
						.orElseThrow(EntityNotFoundException::new);
		
		System.out.println("등록시간 : " + member.getRegTime());
		System.out.println("수정시간 : " + member.getUpDateTime());
		System.out.println("등록자 : " + member.getCreatedBy());
		System.out.println("수정자 : " + member.getModefiedBy());
		// 매번 일일이 안넣어도 바로 가져올 수 있어진다.
	}
	
}
