package com.jsp.spring_project_ticket_booking.service;

import com.jsp.spring_project_ticket_booking.dto.UserDto;

public interface RedisService {
	void saveUserDto(String email, UserDto userDto);

	void saveOtp(String email, int otp);

	UserDto getDtoByEmail(String email);

	int getOtpByEmail(String email);
}
