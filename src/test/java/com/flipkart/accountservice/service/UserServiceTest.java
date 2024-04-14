package com.flipkart.accountservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.flipkart.accountservice.entity.UserEntity;
import com.flipkart.accountservice.model.User;
import com.flipkart.accountservice.model.UserResponse;
import com.flipkart.accountservice.repository.UserRepository;
import com.flipkart.accountservice.status.EntityStatus;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateUser() {
		// Mocking the UserRepository save method
		UserEntity savedEntity = new UserEntity();
		savedEntity.setUid(UUID.randomUUID());
		savedEntity.setCreated(Timestamp.from(Instant.now()));
		savedEntity.setStatus(EntityStatus.CREATED);
		savedEntity.setIsActive(true);
		when(userRepository.save(any(UserEntity.class))).thenReturn(savedEntity);

		// Creating a user to be saved
		User user = new User();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setUsername("johndoe");

		// Calling the createUser method
		User createdUser = userService.createUser(user);

		// Verifying the userRepository save method was called
		verify(userRepository, times(1)).save(any(UserEntity.class));

		// Verifying the returned user
		Assertions.assertEquals(user.getFirstName(), createdUser.getFirstName());
		Assertions.assertEquals(user.getLastName(), createdUser.getLastName());
		Assertions.assertEquals(user.getUsername(), createdUser.getUsername());
	}

	@Test
	public void testGetAllUser() {
		// Creating mock user entities and setting up the UserRepository findAll method
		UserEntity userEntity1 = new UserEntity();
		userEntity1.setUid(UUID.randomUUID());
		userEntity1.setFirstName("John");
		userEntity1.setLastName("Doe");
		userEntity1.setUsername("johndoe");

		UserEntity userEntity2 = new UserEntity();
		userEntity2.setUid(UUID.randomUUID());
		userEntity2.setFirstName("Jane");
		userEntity2.setLastName("Smith");
		userEntity2.setUsername("janesmith");

		List<UserEntity> userEntities = new ArrayList<>();
		userEntities.add(userEntity1);
		userEntities.add(userEntity2);

		Page<UserEntity> page = new PageImpl<>(userEntities);
		when(userRepository.findAll(any(PageRequest.class))).thenReturn(page);

		// Calling the getAllUser method
		UserResponse response = userService.getAllUser(0, 10, "firstName", "ASC");
		List<User> users = response.getUsers();

		// Verifying the userRepository findAll method was called
		verify(userRepository, times(1)).findAll(any(PageRequest.class));

		// Verifying the returned users
		Assertions.assertEquals(2, users.size());
		Assertions.assertEquals(userEntity1.getFirstName(), users.get(0).getFirstName());
		Assertions.assertEquals(userEntity1.getLastName(), users.get(0).getLastName());
		Assertions.assertEquals(userEntity1.getUsername(), users.get(0).getUsername());
		Assertions.assertEquals(userEntity2.getFirstName(), users.get(1).getFirstName());
		Assertions.assertEquals(userEntity2.getLastName(), users.get(1).getLastName());
		Assertions.assertEquals(userEntity2.getUsername(), users.get(1).getUsername());
	}

	@Test
	public void testGetUserById() {
		// Creating a mock user entity and setting up the UserRepository findById method
		UUID userId = UUID.randomUUID();
		UserEntity userEntity = new UserEntity();
		userEntity.setUid(userId);
		userEntity.setFirstName("John");
		userEntity.setLastName("Doe");
		userEntity.setUsername("johndoe");

		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(userEntity));

		// Calling the getUserById method
		User user = userService.getUserById(userId);

		// Verifying the userRepository findById method was called
		verify(userRepository, times(1)).findById(any(UUID.class));

		// Verifying the returned user
		Assertions.assertEquals(userEntity.getFirstName(), user.getFirstName());
		Assertions.assertEquals(userEntity.getLastName(), user.getLastName());
		Assertions.assertEquals(userEntity.getUsername(), user.getUsername());
	}

	@Test
	public void testDeleteUserById() {
		// Creating a mock user entity and setting up the UserRepository existsById and
		// deleteById methods
		UUID userId = UUID.randomUUID();
		when(userRepository.existsById(any(UUID.class))).thenReturn(true);

		// Calling the deleteUserById method
		userService.deleteUserById(userId);

		// Verifying the userRepository existsById and deleteById methods were called
		verify(userRepository, times(1)).existsById(any(UUID.class));
		verify(userRepository, times(1)).deleteById(any(UUID.class));
	}

	@Test
	public void testUpdateUser() {
		// Creating a mock user entity and setting up the UserRepository existsById and
		// save methods
		UUID userId = UUID.randomUUID();
		UserEntity userEntity = new UserEntity();
		userEntity.setUid(userId);
		userEntity.setFirstName("John");
		userEntity.setLastName("Doe");
		userEntity.setUsername("johndoe");
		userEntity.setIsActive(true);
		userEntity.setStatus(EntityStatus.CREATED);

		when(userRepository.existsById(any(UUID.class))).thenReturn(true);
		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(userEntity));
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

		// Creating a user with updated fields
		User updatedUser = new User();
		updatedUser.setFirstName("John Updated");
		updatedUser.setLastName("Doe Updated");
		updatedUser.setUsername("johndoe");

		// Calling the updateUser method
		User result = userService.updateUser(userId, updatedUser);

		// Verifying the userRepository existsById, findById, and save methods were
		// called
		verify(userRepository, times(1)).existsById(any(UUID.class));
		verify(userRepository, times(1)).findById(any(UUID.class));
		verify(userRepository, times(1)).save(any(UserEntity.class));

		// Verifying the returned user
		Assertions.assertEquals(updatedUser.getFirstName(), result.getFirstName());
		Assertions.assertEquals(updatedUser.getLastName(), result.getLastName());
		Assertions.assertEquals(updatedUser.getUsername(), result.getUsername());
	}

	@Test
	public void testPasswordReset() {
		// Creating a mock user entity and setting up the UserRepository existsById and
		// findById methods
		UUID userId = UUID.randomUUID();
		UserEntity userEntity = new UserEntity();
		userEntity.setUid(userId);
		userEntity.setFirstName("John");
		userEntity.setLastName("Doe");
		userEntity.setUsername("johndoe");
		userEntity.setPassword("oldpassword");

		when(userRepository.existsById(any(UUID.class))).thenReturn(true);
		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(userEntity));

		// Creating a user with updated password
		User updatedUser = new User();
		updatedUser.setPassword("newpassword");

		// Calling the passwordReset method
		User result = userService.passwordReset(userId, updatedUser);

		// Verifying the userRepository existsById and findById methods were called
		verify(userRepository, times(1)).existsById(any(UUID.class));
		verify(userRepository, times(1)).findById(any(UUID.class));

		// Verifying the returned user
		Assertions.assertEquals(updatedUser.getPassword(), result.getPassword());
	}
}
