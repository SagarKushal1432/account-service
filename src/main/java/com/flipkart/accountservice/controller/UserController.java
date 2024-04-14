package com.flipkart.accountservice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flipkart.accountservice.model.User;
import com.flipkart.accountservice.model.UserResponse;
import com.flipkart.accountservice.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(path = "/v1/user")
	private ResponseEntity<User> userAccount(@RequestBody User user) {
		User userResponse = userService.createUser(user);
		return ResponseEntity.ok().body(userResponse);
	}
	
	@GetMapping(path = "/v1/test/{num}")
	public ResponseEntity getValue(@PathVariable("num") String num) {
		String test = userService.test(num);
		return ResponseEntity.ok().body(test);
		
	}

	@GetMapping(path = "/v1/user")
	private ResponseEntity<UserResponse> getAllUser(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(name = "sortBy", defaultValue = "username") String sortBy,
			@RequestParam(name = "order", defaultValue = "desc") String order) {
		UserResponse allUser = userService.getAllUser(pageNo, pageSize, sortBy, order);
		return ResponseEntity.ok().body(allUser);
	}

	@GetMapping(path = "/v1/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") UUID id) {
		User user = null;
		user = userService.getUserById(id);
		return ResponseEntity.ok().body(user);
	}

	@DeleteMapping(path = "/v1/user/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable("id") UUID id) {
		userService.deleteUserById(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);

	}

	@PutMapping(path = "/v1/user/{id}")
	public ResponseEntity<User> updaeUser(@PathVariable("id") UUID id, @RequestBody User user) {
		User updateUser = userService.updateUser(id, user);
		return ResponseEntity.ok(updateUser);

	}
	
	@PatchMapping(value = "/v1/user/{id}")
	public ResponseEntity<User> passwordReset(@PathVariable("id") UUID id,  @RequestBody User user){
		User passwordReset = userService.passwordReset(id,user);
		return ResponseEntity.ok(passwordReset);
	}

}