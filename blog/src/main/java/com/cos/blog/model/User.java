package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity //User 클래스가 DB 테이블에 자동으로 생성된다.

//@DynamicInsert // insert시에 null인 필드를 제외시켜준다.
public class User {
	
	@Id//Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀀스, auto_increment
	
	@Column(nullable = false, length = 30)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) //123456 => 해쉬 (비밀번호 암호화)
	private String password; // 비밀번호
	
	@Column(nullable = false, length = 50) 
	private String email; //이메일
	
	//@ColumnDefault("'user'") //문자라는 것을 알려주기 위해 "" 내에 홑따옴표 '' 를 넣어준다.
	//DB는 RoleType이 없기 때문에 해당 Enum 이 String 이란걸 알려줘야 한다.
	@Enumerated(EnumType.STRING)
	private RoleType role; // ADMIN, USER, MANAGER//Enum 전략을 쓰는게 좋다.
	
	@CreationTimestamp //시간이 자동입력
	private Timestamp createDate;
	
}
