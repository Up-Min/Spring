package com.myshop.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.constant.Role;
import com.myshop.dto.MemberFormDto;
import com.myshop.entity.Member;

@SpringBootTest
@Transactional // 테스트 실행을 롤백 처리하기 때문에, 실제로 DB에 들어가는 데이터가 없어짐
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

	@Autowired
	MemberService memberService;
	@Autowired
	PasswordEncoder passwordEncoder;

	public Member createMember() {
		// 테스트 환경이라 일단 적당히 써볼거다
		MemberFormDto member = new MemberFormDto(); // 생성자 member를 가져옴.
		member.setName("홍");
		member.setEmail("h@h.com");
		member.setAddress("홍익대학교");
		member.setPassword("1234");	
		return Member.createMember(member, passwordEncoder);
		//member로 넘어가서 member객체에 저장해주고 member객체를 반환해줄거임.		
	}
	
	@Test
	@DisplayName("회원가입 테스트")
	public void saveMemberTest() {
		//위의 createMember 에서 요청하여 
		// Member.java에서 리턴받은 애를 그대로 넣어준다.
		Member member = createMember();
		//memberService.Java 에있는 saveMember를 가져올거임.
		Member savedmember = memberService.saveMember(member);
		//Member table에 Member.java에서 리턴받은 아이를 넣어줄거다.
		
		assertEquals(member.getEmail(), savedmember.getEmail()); // 테스트 코드를 사용할 때 값을 비교해주는 아이.
		// 내가 여기서 넣은 값이랑, 51번줄을 통해 실제 insert - save된 값이 동일한지 비교할거다!
		assertEquals(member.getName(), savedmember.getName());
		assertEquals(member.getAddress(), savedmember.getAddress());
		assertEquals(member.getPassword(), savedmember.getPassword());
		assertEquals(member.getRole(), savedmember.getRole());
	}	
	
	//Memberservice.java 에 넣은 이메일 중복체크가 잘 되는지 확인
	@Test
	@DisplayName("중복체크 테스트")
	public void saveDuplicateMemberTest() {
		Member member1 = createMember();
		Member member2 = createMember();
		// 멤버 1,2 선언 (중복임)
		
		memberService.saveMember(member1);
		// 멤버 회원가입 시킴
		
	//assertthrow를 쓰면 예외처리 테스트가 가능하다.
		Throwable e = assertThrows(IllegalStateException.class, () -> {
			// 여기서 작성한 코드가 에러가 발생하면, IllegalStateException을 작동시킨다
			memberService.saveMember(member2);
			// insert 하는 도중에 에러가 발생하면 에러가 걸리게 될 것이다.
			// Memberservice.java에서 중복체크중에 에러가 발생하게 될 경우에 
			// throw new IllegalStateException이 throw 되기 때문에
			// assertThrows에도 동일한 에러를 작동시키게 한다.
		});
		// 내가 집어넣은 기준 문구랑, 예외처리가 발생할 때 나오는 문구랑 동일한지 확인해주기.
		assertEquals("이미 가입된 회원입니다.", e.getMessage());
		// 예외처리된 아이의 메세지를 찍어주는 e.getMessage
	}
}
