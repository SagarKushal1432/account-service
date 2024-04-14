package com.flipkart.accountservice.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.flipkart.accountservice.status.EntityStatus;

import lombok.Data;

@Data
public class BaseEntity {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID uid;
	private Timestamp created;
	private Timestamp updated;
	@Enumerated(EnumType.STRING)
	private EntityStatus status;
}