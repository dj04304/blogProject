package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * static 에는 정적인 파일들만 놓는 곳이다. png, html 등등 
 * 하지만 jsp파일은 인식하지 못하는데,  이 때 확인해야 할 것이
 * 1. jsp 템플릿이 pom.xml에 제대로 설치되어 있는지
 * 2. yml경로를 제대로 설정했는지
 */


@Controller
public class TempController {

	//http://localhost:8080/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		//파일리턴 기본경로: src/main/resources/static
		//리턴명: /home.html
		//풀 경로: src/main/resources/static/home.html
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/content_img05.png";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		return "/test.jsp";
	}
	
}
