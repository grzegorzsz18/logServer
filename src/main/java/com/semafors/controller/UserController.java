package com.semafors.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.semafors.entity.User;
import com.semafors.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired UserService userService;
	
	@PostMapping("add")
	public void add(@RequestBody User user) {
		userService.addUser(user);
	}
	
	@GetMapping("/byId/{userId}")
	public User getById(@PathVariable("userId") long userId) {
		return userService.getById(userId);
	}
	
	@GetMapping("/allActive")
	public List<User> getActiveUsers(){
		return userService.getActiveUsers();
	}
}
