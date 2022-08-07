package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML 파일)
//@Controller

//사용자가 요청 -> 응답(Data)
@RestController
public class HttpController {
	
	private static final String TAG = "HttpControllerTest: ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m1 = new Member().builder()
					.username("준형")
					.password("1234")
					.email("dj043045@gmail.com")
					.build();
		System.out.println(TAG + "getter: " + m1.getUsername());
		m1.setUsername("cos");
		System.out.println(TAG + "getter: " + m1.getUsername());
		
		return "lombok test 완료";
	}
	
		//인터넷 브라우저 요청은 무조건 get요청만 할 수 있다.
		//http://localhost:8080/blog/http/get (select)
	@GetMapping("/http/get")
	public String getTest(Member m) { // id=1&username=jun&password=1234&email=dj043045@gmail.com //MessageConverter(스프링부트)
		
		
		return "get 요청:  " + m.getId() + "," + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
	}
	
	//http://localhost:8080/http/post (insert)
	@PostMapping("/http/post") // MimeType: text/plain, application/json
	public String postTest(@RequestBody Member m) { //MessageConverter(스프링부트) 가 알아서 json으로 parsing해준다.
		
		return "post 요청:  " + m.getId() + "," + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
	}
	
	//http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청:  " + m.getId() + "," + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
	}
	
	//http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
}
