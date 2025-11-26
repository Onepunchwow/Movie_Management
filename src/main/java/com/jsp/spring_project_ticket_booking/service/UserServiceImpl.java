package com.jsp.spring_project_ticket_booking.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.spring_project_ticket_booking.dto.LoginDto;
import com.jsp.spring_project_ticket_booking.dto.UserDto;
import com.jsp.spring_project_ticket_booking.entity.User;
import com.jsp.spring_project_ticket_booking.repository.UserRepository;
import com.jsp.spring_project_ticket_booking.util.AES;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	
	private final UserRepository userRepository;
	
	@Override
	public String register(@Valid UserDto userDto, BindingResult result) {
		if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
			result.rejectValue("confirmPassword", "error..confirmPassword", "* Password and confirm password should be same");
		}
		if(result.hasErrors()) {
			return "register.html";
		}else {
			return "main.html";
		}
	}

	@Override
	public String login(LoginDto loginDto, RedirectAttributes attributes, HttpSession session) {
		User user = userRepository.findByEmail(loginDto.getEmail());
		if(user == null) {
			attributes.addFlashAttribute("fail", "Invalid email");
			return "redirect:/login";
		}else {
			if(AES.decrypt(user.getPassword()).equals(loginDto.getPassword())) {
				session.setAttribute("user", user);
				attributes.addFlashAttribute("pass", "Login sucess");
				return "redirect:/";
			}else {
				attributes.addFlashAttribute("fail", "Invalid password");
				return "redirect:/login";
			}
		}
	}

}
