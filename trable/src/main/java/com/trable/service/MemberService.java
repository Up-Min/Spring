package com.trable.service;

import java.io.File;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trable.dto.MemberFormDto;
import com.trable.entity.Member;
import com.trable.entity.MemberImg;
import com.trable.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void checkemail(Member member) {
		Member findmember = memberRepository.findByEmail(member.getEmail());
		if(findmember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}
	
	public Member saveMember(Member member, File file) {
		checkemail(member);
		memberRepository.save(member);
		
		MemberImg memberimg = new MemberImg();
		memberimg.setMember(member);
		
		
		
		
	}
	
	
}
