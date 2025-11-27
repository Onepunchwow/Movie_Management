package com.jsp.spring_project_ticket_booking.service;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.spring_project_ticket_booking.dto.LoginDto;
import com.jsp.spring_project_ticket_booking.dto.UserDto;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public interface UserService {

	String register(@Valid UserDto userDto, BindingResult result);

	String login(LoginDto loginDto, RedirectAttributes attributes, HttpSession session);

	String logout(HttpSession session, RedirectAttributes attributes);

}
