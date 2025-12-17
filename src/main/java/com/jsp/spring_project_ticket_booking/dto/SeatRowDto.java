package com.jsp.spring_project_ticket_booking.dto;

import lombok.Data;

@Data
public class SeatRowDto {
	private String rowName;
	private Integer totalSeats;
	private String category;
}
