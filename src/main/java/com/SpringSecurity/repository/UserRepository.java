package com.SpringSecurity.repository;

import com.SpringSecurity.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);


	User findByEmailIgnoreCase(String emailId);

	Boolean existsByEmail(String email);
}
