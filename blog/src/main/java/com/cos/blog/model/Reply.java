package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Reply { //댓글
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int Id;
	
	@Column(nullable = false, length = 200)
	private String content;
	
	@ManyToOne //여러개의 답변이 하나의 게시글에 등록된다.
	@JoinColumn(name = "boardId")
	private Board board;
	
	@ManyToOne //여러개의 답변을 하나의 유저가 사용할 수 있다.
	@JoinColumn(name = "userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
}
