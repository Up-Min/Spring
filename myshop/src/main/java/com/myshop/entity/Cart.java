package com.myshop.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart") // 테이블 이름
@Getter
@Setter
@ToString
public class Cart {

		@Id
		@Column (name = "cart_id")
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		// 서로 참조 관계일 때, 자식 엔티티에 부모 엔티티를 가져오도록 선언한다.

		@OneToOne (fetch = FetchType.LAZY) // member과 cart의 관계가 1:1로 됨을 선언 함.
		@JoinColumn (name = "member_id")// join 관계에 있는 컬럼 지정
		// 명시를 해줌으로서 관계성이 생긴다. 
		private Member member; //멤버 엔티티 가져오기
		
		
}
