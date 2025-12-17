package com.jsp.spring_project_ticket_booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.spring_project_ticket_booking.entity.Screen;
import com.jsp.spring_project_ticket_booking.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long>{

	List<Seat> findByScreenOrderBySeatRowAscSeatColumnAsc(Screen screen);

}
