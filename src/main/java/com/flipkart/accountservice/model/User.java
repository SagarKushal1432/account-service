package com.flipkart.accountservice.model;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.flipkart.accountservice.status.EntityStatus;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class User {
	private UUID uid;
	@JsonAlias(value = "first_name")
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private Boolean isActive;
	private Timestamp created;
	private Timestamp updated;
	private EntityStatus status;
}