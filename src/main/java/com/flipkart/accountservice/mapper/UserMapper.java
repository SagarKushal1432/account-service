package com.flipkart.accountservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flipkart.accountservice.entity.UserEntity;
import com.flipkart.accountservice.model.User;

@Mapper
public interface UserMapper {
	public UserEntity toEntity(User account);
	public List<User> toModel(List<UserEntity> userEntity);
	//@Mapping(ignore = true, source = "password", target = "password")
	public User toModel(UserEntity userEntity);
}