package com.jsp.spring_project_ticket_booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.spring_project_ticket_booking.entity.Screen;
import com.jsp.spring_project_ticket_booking.entity.Shows;

public interface ShowsRepository extends JpaRepository<Shows, Long>{

	List<Shows> findByScreen(Screen screen);

}
