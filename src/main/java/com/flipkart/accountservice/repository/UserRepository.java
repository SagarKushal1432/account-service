package com.flipkart.accountservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flipkart.accountservice.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}