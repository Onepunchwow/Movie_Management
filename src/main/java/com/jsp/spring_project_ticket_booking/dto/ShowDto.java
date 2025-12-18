package com.jsp.spring_project_ticket_booking.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ShowDto {
	private LocalDate showDate;
	private LocalTime startTime;
	private Long movieId;
	private Double ticketPrice;
	private Long screenId;
}
