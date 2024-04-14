package com.flipkart.accountservice.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.flipkart.accountservice.entity.UserEntity;
import com.flipkart.accountservice.exceptionHandler.EmptyResultDataAccessException;
import com.flipkart.accountservice.exceptionHandler.NoSuchElementException;
import com.flipkart.accountservice.mapper.UserMapper;
import com.flipkart.accountservice.model.User;
import com.flipkart.accountservice.model.UserResponse;
import com.flipkart.accountservice.repository.UserRepository;
import com.flipkart.accountservice.status.EntityStatus;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User createUser(User user) {
		UserMapper userMapper = Mappers.getMapper(UserMapper.class);
		UserEntity entity = userMapper.toEntity(user);
		entity.setCreated(Timestamp.from(Instant.now()));
		entity.setStatus(EntityStatus.CREATED);
		entity.setIsActive(true);
		UserEntity userEntity = userRepository.save(entity);
		User model = userMapper.toModel(userEntity);
		return model;
	}
	
	public String test(String num) {
			double value = 0;
			try {
				value = Double.parseDouble(num);
			}catch(Exception e) {
				value = 0;
			}
			
			DecimalFormat format = new DecimalFormat("#,###");
			return ("$" + format.format(value));
			
		
	}

	public UserResponse getAllUser(int pageNo, int pageSize, String sortBy, String order) {
		Sort sort = "DESC".equalsIgnoreCase(order) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<UserEntity> page = userRepository.findAll(pageable);
		List<UserEntity> userEntities = page.toList();
		UserMapper userMapper = Mappers.getMapper(UserMapper.class);
		List<User> users = userMapper.toModel(userEntities);
		UserResponse response = new UserResponse();
		response.setCount(page.getTotalElements());
		response.setUsers(users);
		return response;

	}

	public User getUserById(UUID id) {
		Optional<UserEntity> optional = userRepository.findById(id);
		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			UserMapper userMapper = Mappers.getMapper(UserMapper.class);
			return userMapper.toModel(userEntity);
		}
		throw new NoSuchElementException("No Record found ");
	}

	public void deleteUserById(UUID id) {
		boolean existsById = userRepository.existsById(id);
		if (existsById == true) {
			userRepository.deleteById(id);
		} else {
			throw new EmptyResultDataAccessException("ID does not exist");
		}

	}

	public User updateUser(UUID id, User user) {
		boolean existsById = userRepository.existsById(id);
		if (existsById) {
			UserEntity userEntity = userRepository.findById(id).get();
			userEntity.setFirstName(user.getFirstName());
			userEntity.setLastName(user.getLastName());
			userEntity.setUsername(user.getUsername());
			userEntity.setIsActive(true);
			userEntity.setStatus(EntityStatus.UPDATED);
			userEntity.setUpdated(Timestamp.from(Instant.now()));
			UserEntity updatedEntity = userRepository.save(userEntity);
			UserMapper mapper = Mappers.getMapper(UserMapper.class);
			User updatedUser = mapper.toModel(updatedEntity);
			return updatedUser;
		}
		throw new EmptyResultDataAccessException("ID does not exist");
	}
	
	public User passwordReset(UUID id, User user) {
		boolean existsById = userRepository.existsById(id);
		if(existsById){
			UserEntity userEntity = userRepository.findById(id).get();
			userEntity.setPassword(user.getPassword());
			UserMapper mapper = Mappers.getMapper(UserMapper.class);
			User updatedUser = mapper.toModel(userEntity);
			return updatedUser;
		}
		throw new EmptyResultDataAccessException("ID does not exist");
	}
}