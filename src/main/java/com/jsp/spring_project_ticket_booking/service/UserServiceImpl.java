package com.jsp.spring_project_ticket_booking.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.spring_project_ticket_booking.dto.LoginDto;
import com.jsp.spring_project_ticket_booking.dto.PasswordDto;
import com.jsp.spring_project_ticket_booking.dto.UserDto;
import com.jsp.spring_project_ticket_booking.entity.User;
import com.jsp.spring_project_ticket_booking.repository.UserRepository;
import com.jsp.spring_project_ticket_booking.util.AES;
import com.jsp.spring_project_ticket_booking.util.EmailHelper;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	
	private final UserRepository userRepository;
	private final SecureRandom secureRandom;
	private final EmailHelper emailHelper;
	private final RedisService redisService;


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
				return "redirect:/main";
			}else {
				attributes.addFlashAttribute("fail", "Invalid password");
				return "redirect:/login";
			}
		}
	}

	@Override
	public String logout(HttpSession session, RedirectAttributes attributes) {
		session.removeAttribute("user");
		attributes.addFlashAttribute("pass","Logout Sucess");
		return "redirect:/main";
	}

	@Override
	public String register(@Valid UserDto userDto, BindingResult result, RedirectAttributes attributes) {
		if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
			result.rejectValue("confirmPassword", "error.confirmPassword", "* Password and confirm password should be same");
		}
		if(userRepository.existsByEmail(userDto.getEmail())) {
			result.rejectValue("email", "error.email", "* Email already exists");
		}
		if(userRepository.existsByMobile(userDto.getMobile())) {
			result.rejectValue("mobile", "error.mobile", "* Mobile already exists");
		}
		if(result.hasErrors()) {
			return "register.html";
		}else {
			int otp = secureRandom.nextInt(100000, 1000000);
			emailHelper.sendOtp(otp, userDto.getName(), userDto.getEmail());
			redisService.saveUserDto(userDto.getEmail(), userDto);
			redisService.saveOtp(userDto.getEmail(), otp);
			attributes.addFlashAttribute("pass", "Otp Sent Success");
			attributes.addFlashAttribute("email", userDto.getEmail());
			return "redirect:/otp";
		}
	}

	@Override
	public String submitOtp(int otp, String email, RedirectAttributes attributes) {
		UserDto dto = redisService.getDtoByEmail(email);
		if (dto == null) {
			attributes.addFlashAttribute("fail", "Timeout Try Again Creating a New Account");
			return "redirect:/register";
		} else {
			int exOtp = redisService.getOtpByEmail(email);
			if (exOtp == 0) {
				attributes.addFlashAttribute("fail", "OTP Expired, Resend Otp and Try Again");
				attributes.addFlashAttribute("email", email);
				return "redirect:/otp";
			} else {
				if (otp == exOtp) {
					User user = new User(null, dto.getName(), dto.getEmail(), dto.getMobile(),
							AES.encrypt(dto.getPassword()), "USER");
					userRepository.save(user);
					attributes.addFlashAttribute("pass", "Account Registered Success");
					return "redirect:/main";

				} else {
					attributes.addFlashAttribute("fail", "Invalid OTP Try Again");
					attributes.addFlashAttribute("email", email);
					return "redirect:/otp";
				}
			}
		}
	}
	
	@Override
	public String resendOtp(String email, RedirectAttributes attributes) {
		UserDto dto = redisService.getDtoByEmail(email);
		if (dto == null) {
			attributes.addFlashAttribute("fail", "Timeout Try Again Creating a New Account");
			return "redirect:/register";
		} else {
			int otp = secureRandom.nextInt(100000, 1000000);
			emailHelper.sendOtp(otp, dto.getName(), dto.getEmail());
			redisService.saveOtp(dto.getEmail(), otp);
			attributes.addFlashAttribute("pass", "Otp Re-Sent Success");
			attributes.addFlashAttribute("email", dto.getEmail());
			return "redirect:/otp";
		}
	}

	@Override
	public String forgotPassword(String email, RedirectAttributes attributes) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			attributes.addFlashAttribute("fail", "Invalid Email");
			return "redirect:/forgot-password";
		} else {
			int otp = secureRandom.nextInt(100000, 1000000);
			emailHelper.sendOtp(otp, user.getName(), email);
			redisService.saveOtp(email, otp);
			attributes.addFlashAttribute("pass", "Sent Success");
			attributes.addFlashAttribute("email", email);
			return "redirect:/reset-password";
		}
	}

	@Override
	public String resetPassword(PasswordDto passwordDto, BindingResult result, RedirectAttributes attributes,ModelMap map) {
		if (result.hasErrors()) {
			map.put("email", passwordDto.getEmail());
			return "reset-password.html";
		}User user = userRepository.findByEmail(passwordDto.getEmail());
		if (user == null) {
			attributes.addFlashAttribute("fail", "Invalid Email");
			return "redirect:/forgot-password";
		} else {
			int exOtp = redisService.getOtpByEmail(passwordDto.getEmail());
			if (exOtp == 0) {
				attributes.addFlashAttribute("fail", "OTP Expired, Resend Otp and Try Again");
				attributes.addFlashAttribute("email", passwordDto.getEmail());
				return "redirect:/reset-password";
			} else {
				if (passwordDto.getOtp() == exOtp) {
					user.setPassword(AES.encrypt(passwordDto.getPassword()));
					userRepository.save(user);
					attributes.addFlashAttribute("pass", "Password Reset Success");
					return "redirect:/main";

				} else {
					attributes.addFlashAttribute("fail", "Invalid OTP Try Again");
					attributes.addFlashAttribute("email", passwordDto.getEmail());
					return "redirect:/reset-password";
				}
			}

		}
	}

}
