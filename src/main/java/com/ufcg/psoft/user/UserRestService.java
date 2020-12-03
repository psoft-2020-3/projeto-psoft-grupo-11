package com.ufcg.psoft.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserRestService {

	@Autowired
	private UserService userService;

	// /user/register
	@PostMapping(value = "/register")
	public User registration(@RequestBody User user) {
		User savedUser = userService.save(user);

		return savedUser;
	}
}