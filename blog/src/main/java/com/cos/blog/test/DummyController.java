package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//html 파일이 아닌, 데이터를 리턴해주는 Controller
@RestController
public class DummyController {
	
	@Autowired //의존성 주입(DI)
	private UserRepository userRepository;
	

	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) { //EmptyResultDataAccessException DB에 비어있을 때의 Exception 
			e.printStackTrace();
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		return "삭제되었습니다. id: " + id;
	}
	
	// save 함수는 id를 전달하지 않으면 insert 해주고
	// save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	// save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.
	//email, password
	
	@Transactional //함수가 호출될 때 시작하고, return될 때 Transactional 이 종료되면서 자동으로 commit이 된다.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // json 데이터를 요청 => JAVA Object(MessageConverter의 Jackson라이브러리가 변환을 해준다.)
		System.out.println("id: " + id);
		System.out.println("password: " + requestUser.getPassword());
		System.out.println("email: " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
//		requestUser.setId(id);
//		requestUser.setUsername("준형");
		// userRepository.save(user); //save는 받아온 id가 기존의 id와 같다면, 덮어버린다.
		
		//더티 체킹
		return user;
	}
	
	//http://localhost:8080/blog/dummy/users
	//데이터 내의 user 전체 값
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll(); // findAll의 리턴타입이 List이다. 
	}
	
	//한 페이지당 2건의 데이터를 리턴받아 볼 예정
	//사이즈는 2건, 정렬순은 id의 역순(최신순)
	//user의 페이징처리
	//http://localhost:8080/blog/dummy/user
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> PagingUser = userRepository.findAll(pageable);
		
		List<User> users = PagingUser.getContent();
		return users;
	}
	
	//{id} 주소로 파라미터를 전달 받을 수 있음
	//http://localhost:8080/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// 만약 user/4 를 찾았을 때, 데이터베이스에서 못찾으면 user 가 null값이 된다.
		// 그럼 return할때 null이 리턴이 되니 프로그램에 문제가 생긴다.
		// 그러므로 Optional로 User객체를 감싸서 가져온다. null인지 아닌지는 판단해서 return
		
		//	람다식
//		User user = userRepository.findById(id).orElseThrow(() -> {
//			return new IllegalArgumentException("해당 사용자는 없습니다.");
//		});
//		return user;
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
					
				return new IllegalArgumentException("해당 유저는 없습니다.");
			}
		});
		// 요청: 웹 브라우저
		// user 객체 = 자바 오브젝트
		// 변환 (웹 브라우저가 이해할 수 있는 데이터) -> JSON
		// 스프링부트 = MessageConverter 라는 애가 응답시에 자동으로 작동한다.
		// 만약에 자바 오브젝트를 리턴하면 MessageConverter가 Jackson이라는 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
		return user;
	}
	
	//http://localhost:8080/blog/dummy/join(요청)
	//http의 body에 username, password, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("id: " + user.getId());
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		System.out.println("role: " + user.getRole());
		System.out.println("createDate: " + user.getCreateDate());
		
		user.setRole(RoleType.User);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
