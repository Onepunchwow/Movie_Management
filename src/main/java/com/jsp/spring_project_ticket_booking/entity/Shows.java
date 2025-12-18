package com.jsp.spring_project_ticket_booking.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Shows {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private LocalDate showDate;
	private LocalTime startTime;
	private LocalTime endTime;
	@ManyToOne
	private Movie movie;
	private Double ticketPrice;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ShowSeat> seats;
	@ManyToOne
	private Screen screen;
}
