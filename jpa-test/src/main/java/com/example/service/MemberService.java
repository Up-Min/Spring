package com.example.service;

import com.example.entity.Member;
import com.example.entity.emf.UniqueEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class MemberService {

	public void save(Member member) {
		// 엔티티 매니저 팩토리 : 애플리케이션 당 하나 이기 때문에, static을 사용한다.
		EntityManagerFactory emf = UniqueEntityManagerFactory.emf;
		// 엔티티 매니저 생성 : 엔티티 매니저 팩토리에서 생성 가능하다.
		EntityManager em = emf.createEntityManager();
		// 트랜잭션 (쪼갤 수 없는 업무의 단위) : 엔티티 매니저에서 트랜잭션을 생성한다.
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin(); // 트랜잭션 시작 + 커넥션 획득
			em.persist(member); // 영속. 엔티티 매니저를 통해 엔티티를 영속성 컨텍스트에 저장.
			// 실제로는 커밋을 할 때 반영이 된다.
			tx.commit(); // 실제 DB에 저장 하는 커밋.
		} catch (Exception e) {
			// 과정에서 에러가 발생했을 경우
			e.printStackTrace(); // 에러 출력
			tx.rollback(); // 커밋하기 직전으로 롤백시킴 (DB)
		}finally {
			// 영속이 있으면 준영속도 있다.
			em.close(); // 준영속 (엔티티가 저장되었다가 분리된 상태, 영속성 컨텍스트가 더이상 관리하지 않음.)
			//em.detach(member);
			//em.clear(); 이거 2개를 써도 무관하다.
		
			// em.remove(member);
			//persistance contact에 있던 엔티티를 삭제시켜버림.
		}
	}
}
