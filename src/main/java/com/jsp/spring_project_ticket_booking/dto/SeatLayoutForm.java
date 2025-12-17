package com.jsp.spring_project_ticket_booking.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SeatLayoutForm {
	private List<SeatRowDto> rows = new ArrayList<>();
}
