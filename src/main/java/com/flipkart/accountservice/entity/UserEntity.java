package com.flipkart.accountservice.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.flipkart.accountservice.status.EntityStatus;

import lombok.Data;

@Data
@Table(name = "account")
@Entity
public class UserEntity{
	@Column(name= "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "username", unique = true)
	private String username;
	private String password;
	private Boolean isActive;
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID uid;
	private Timestamp created;
	private Timestamp updated;
	@Enumerated(EnumType.STRING)
	private EntityStatus status;
}