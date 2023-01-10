package com.example.entity;

import java.util.Date;

import javax.persistence.*;

import com.example.constant.MemberClass;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
	@Id 
	@Column (name = "member_id") //DB의 하나의 컬럼에 해당, 이름을 member_id로 넣어주겠다는 말임.
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column 
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column
	private MemberClass memberclass;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;	
}

/*
 * Column : db의 컬럼으로 쓰겠다. 이름은 선언한 변수 명으로 들어가지만, 추가 name을 지정할 수도 있다.
 * Enumerated: EnumType열거형 클래스를 컬럼으로 이용하기 위해 꼭 붙여줘야 한다.
 * Temporal : 날짜타입을 맵핑할 때 사용한다.
 * @Id : PK를 사용할 아이에게 붙여준다.
 * @Entity : 아래에 선언한 아이들을 엔티티로 이용한다고 선언하는 것.
 */