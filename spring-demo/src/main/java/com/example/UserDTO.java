package com.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// rombok 사용. annotation 붙인다
@Getter
@Setter
@ToString //object overide할 때 객체의 정보를 출력하는 아이였다.
public class UserDTO {
	private String name;
	private int age;
}
