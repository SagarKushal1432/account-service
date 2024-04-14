package com.flipkart.accountservice.model;

import java.util.List;

import lombok.Data;

@Data
public class UserResponse {
	
	private Long count;
	private List<User> users;

}
