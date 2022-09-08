package com.opplus.demoUser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opplus.demoUser.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	
	
}

