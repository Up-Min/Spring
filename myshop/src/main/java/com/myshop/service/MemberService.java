

package com.myshop.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class MemberService implements UserDetailsService {
	// UserDetailsService : 로그인시 request에서 넘어온 사용자 정보를 받음.
	
	private final MemberRepository memberRepository; //의존성 주입.
		
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// String username -> String email : email에 대한 정보를 본인이 알아서 가져온다.
		Member member = memberRepository.findByEmail(email); 
		// 있는지 없는지 확인을 해야한다. 그래서 먼저 찾아본다 (있으면 로그인!)
		
		if(member == null) {
			// 멤버 값이 없는 경우 에러를 throw 해준다.
			throw new UsernameNotFoundException(email);
		}
		
		
		// 사용자 정보가 있을경우. DB에서 받은 userDetail에서의 사용자 객체를 반환해준다.
		return User.builder()
				.username(member.getEmail())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
		// 아 얘가 springsecuritycontext에 저장이 되는구나!하고 이해하면 된다.
		
	}
	
	
	
	public Member saveMember(Member member) {
		validateDuplicateMember(member); // 이메일 중복체크 우선. 
		// 만약 있을 경우 exception 날릴거임
		return memberRepository.save(member); //member 테이블에 insert
	}
	
	// 이메일 중복체크 메소드
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByEmail(member.getEmail());
		if(findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}
	
	
	
	
	
	
	
}
