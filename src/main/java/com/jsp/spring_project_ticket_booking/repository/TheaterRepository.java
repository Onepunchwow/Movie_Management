package com.jsp.spring_project_ticket_booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.spring_project_ticket_booking.entity.Theater;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

	boolean existsByNameAndAddress(String name, String address);

}
