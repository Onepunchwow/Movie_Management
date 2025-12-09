package com.jsp.spring_project_ticket_booking.service;

import java.io.IOException;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.spring_project_ticket_booking.dto.LoginDto;
import com.jsp.spring_project_ticket_booking.dto.PasswordDto;
import com.jsp.spring_project_ticket_booking.dto.ScreenDto;
import com.jsp.spring_project_ticket_booking.dto.TheaterDto;
import com.jsp.spring_project_ticket_booking.dto.UserDto;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public interface UserService {

	String register(@Valid UserDto userDto, BindingResult result, RedirectAttributes attributes);

	String login(LoginDto loginDto, RedirectAttributes attributes, HttpSession session);

	String logout(HttpSession session, RedirectAttributes attributes);
	
	String submitOtp(int otp, String email, RedirectAttributes attributes);

	String forgotPassword(String email, RedirectAttributes attributes);

	String resendOtp(String email, RedirectAttributes attributes);

	String resetPassword(@Valid PasswordDto passwordDto, BindingResult result, RedirectAttributes attributes,
			ModelMap map);

	String manageUsers(HttpSession session, RedirectAttributes attributes, ModelMap map);

	String blockUser(Long id, HttpSession session, RedirectAttributes attributes);

	String unBlockUser(Long id, HttpSession session, RedirectAttributes attributes);

	String manageTheater(ModelMap map, RedirectAttributes attributes, HttpSession session);

	String loadAddTheater(HttpSession session, RedirectAttributes attributes, TheaterDto theaterDto);

	String addTheater(HttpSession session, RedirectAttributes attributes, @Valid TheaterDto theaterDto,
			BindingResult result) throws IllegalStateException, IOException;

	String editTheater(Long id, HttpSession session, RedirectAttributes attributes, ModelMap map);

	String updateTheater(HttpSession session, RedirectAttributes attributes, @Valid TheaterDto theaterDto,
			BindingResult result, Long id);

	String manageScreens(Long id, HttpSession session, RedirectAttributes attributes, ModelMap map);

	String addScreen(Long id, HttpSession session, RedirectAttributes attributes, ModelMap map, ScreenDto screenDto);
	
	String addScreen(ScreenDto screenDto, BindingResult result,HttpSession session, RedirectAttributes attributes);

	String deleteTheater(Long id, HttpSession session, RedirectAttributes attributes);

}
