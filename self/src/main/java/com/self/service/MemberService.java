package com.self.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.self.entity.Member;
import com.self.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
	
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email);
		
		if(member == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return User.builder()
				.username(member.getEmail())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}
	
	private void chkEmail(Member member) {
		Member getmember = memberRepository.findByEmail(member.getEmail());
		System.out.println("getmember : " + getmember);
		if(getmember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}
	
	
	public Member saveMember(Member member) {
			chkEmail(member);
			return memberRepository.save(member);
	}
	
	
		
	
}
