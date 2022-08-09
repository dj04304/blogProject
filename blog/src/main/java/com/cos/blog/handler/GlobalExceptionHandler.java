package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	//exception이 발생했을 때 handler가 실행
	@ExceptionHandler(value = Exception.class) // IllegalArgumentException 이 발생하면 e 에 전달하여 return이 실행 // 모든 Exception이 다 여기로잡힘
	public String handleArgumentException(Exception e) {
		return "<h1>" + e.getMessage() + "</h1>";
	}
	
}
