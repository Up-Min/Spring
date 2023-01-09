package com.example.springquiz;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

////////////////
@Data // 위의 3개 이거 하나로 할 수 있다.


public class StudentDto {
	
	private String name;
	private int age;
	private String JavaGrade;
	private String oracleGrade;
	
	
}
