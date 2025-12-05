package com.jsp.spring_project_ticket_booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.spring_project_ticket_booking.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	boolean existsByEmail(String email);
	
	User findByEmail(String email);

	boolean existsByMobile(Long mobile);
	
	List<User> findByRole(String role);
}
