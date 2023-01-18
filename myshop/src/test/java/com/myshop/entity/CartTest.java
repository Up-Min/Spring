package com.myshop.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.dto.MemberFormDto;
import com.myshop.repository.CartRepository;
import com.myshop.repository.MemberRepository;
import com.myshop.service.MemberService;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartTest {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	PasswordEncoder passwordEncoder;	
	@PersistenceContext
	EntityManager em;
	// 영속성 컨텍스트에 엔티티 저장. 엔티티 매니저를 통해 엔티티에 접근.
	
	public Member createMember() {
		MemberFormDto member = new MemberFormDto(); // 생성자 member를 가져옴.
		member.setName("홍");
		member.setEmail("test@email.com");
		member.setAddress("홍익대학교");
		member.setPassword("1234");	
		
		return Member.createMember(member, passwordEncoder);
		//memberservice 의 savenumber -> table 추가.		
	}
	
	
	// 장바구니, 회원 잘 조회되는지 확인해보기
	@Test
	@DisplayName("장바구니 회원 엔티티 맵핑 조회 테스트")
	public void findCartAndMemberTest() {
		Member member = createMember(); // 멤버 생성
		memberRepository.save(member); // 영속성 컨텍스트에 멤버 저장 
		
		Cart cart = new Cart(); // cart 선언
		cart.setMember(member); // cart에 멤버 넣음 
		cartRepository.save(cart); // 영속성 컨텍스트에 cart 저장. 회원이 자신의 장바구니를 갖게 됨.
		
		em.flush(); // 트랜잭션이 끝날 때 영속성 컨텍스트에 저장된 것을 DB에 반영 (from 쓰기 지연 SQL 저장소)
		em.clear(); // 영속성 컨텍스트에서 엔티티를 빼줌. 
		// 실제 DB에서 cart 엔티티를 가지고 올 때, member 엔티티도 같이 가지고 오는지를 보기 위해?
		// 엔티티를 클리어해줘서 없으니까, 엔티티가 아닌 DB에서 둘 다 가지고 오는지를 확인할 수 있다! 
		// 엔티티 생명주기 flush, clear 참조. 
		
		Cart savedCart = cartRepository.findById(cart.getId()) 
						//optional이라는 클래스를 리턴해줌. ppt 참고
						.orElseThrow(EntityNotFoundException::new); //에러 던짐. (옵셔널)
		
		assertEquals(savedCart.getMember().getId(), member.getId()); 
		//DB에서 가져온 id와 처음 넣은 member의 id가 동일한지를 비교.
		
			
	}
}
