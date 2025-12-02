package com.jsp.spring_project_ticket_booking.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jsp.spring_project_ticket_booking.entity.User;
import com.jsp.spring_project_ticket_booking.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class AdminRegisteration implements CommandLineRunner{
	@Value("${admin.password}")
	private String password;
	@Value("${admin.email}")
	private String email;
	
	private final UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		
		if(!userRepository.existsByEmail(email)) {
			User user = new User(null, "ADMIN", email, 0L, AES.encrypt(password), "ADMIN");
			userRepository.save(user);
			log.info("User registered sucessfully");
		}else {
			log.info("Admin exists");
		}
		
	}

}
