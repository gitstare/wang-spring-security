package com.example.demo;

import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	@Test
	public void contextLoads() {
	}

//	@Test
//	public void test1() {
//		String pass = MD5Util.encode("a");
//		User user = new User("a",pass);
//		List<Role> roles = new ArrayList<>();
//		roles.add(roleRepository.findByRolename("ROLE_ADMIN"));
//		user.setRoles(roles);
//		repository.save(user);
//
//	}

//
//	}
}
