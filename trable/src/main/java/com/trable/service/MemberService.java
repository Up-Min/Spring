package com.trable.service;

import java.io.File;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trable.constant.UserGrade;
import com.trable.dto.MemberFormDto;
import com.trable.entity.Member;
import com.trable.repository.MemberRepository;

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
				.roles(member.getUsergrade().toString())
				.build();
	}
	
	private void checkemail(Member member) {
		Member findmember = memberRepository.findByEmail(member.getEmail());
		if(findmember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}
	
	public Member saveMember(Member member) throws Exception {
		checkemail(member);
		return memberRepository.save(member);
	}

	public Member findMember(String email) {
		return memberRepository.findByEmail(email);
	}
	
	public Member findMemberbyId(Long Memberid) {
		Member member =  memberRepository.findById(Memberid).orElseThrow(EntityNotFoundException::new);
		return member;
	}
	
	public void updateMemberpwd(Long Memberid, String pw, PasswordEncoder passwordEncoder) {
		Member member =  memberRepository.findById(Memberid).orElseThrow(EntityNotFoundException::new);
		member.updatepwd(pw, passwordEncoder);
		memberRepository.save(member);
	}
	
	public Member updateMembergrade(Long Memberid, int heart) {
		Member member =  memberRepository.findById(Memberid).orElseThrow(EntityNotFoundException::new);	
		if(heart<= 100 && heart>=0) {
			member.updategrade(UserGrade.ONE);
		}else if (heart <= 300 && heart >= 101) {
			member.updategrade(UserGrade.TWO);
		}else if(heart <= 500 && heart >= 301) {
			member.updategrade(UserGrade.THREE);
		}else if(heart >= 501) {
			member.updategrade(UserGrade.FOUR);
		}
		memberRepository.save(member);
		return member;
	}
	
	public void deletemember(Long memberid) {
		Member member = memberRepository.findById(memberid).orElseThrow(EntityNotFoundException::new);
		memberRepository.delete(member);
	}
	
}
