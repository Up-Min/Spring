

package com.myshop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.entity.Member;
import com.myshop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional 
// db transaction과 동일함. 로직을 처리하다 이 클래스에서 에러가 발생하면 콜백을 실시한다.

@RequiredArgsConstructor //final 키워드의 의존성을 주입하기 위해서는 애를 사용해야한다.
// 원래는 autowired를 썼었지만, final을 쓰려면 얘를 써야한다.
public class MemberService {
	private final MemberRepository memberRepository; //의존성 주입.
	
	public Member saveMember(Member member) {
		return memberRepository.save(member); //member 테이블에 insert
	}
	
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByEmail(member.getEmail());
		if(findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}
	
	
	
	
	
}
