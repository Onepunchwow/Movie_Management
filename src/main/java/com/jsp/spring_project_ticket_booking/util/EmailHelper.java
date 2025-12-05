package com.jsp.spring_project_ticket_booking.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Async
public class EmailHelper {
	
	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;

	public void sendOtp(int otp, String name, String email) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		try {
			helper.setFrom("book-my-ticket.com", "Book-My-Tiket");
			helper.setTo(email);
			helper.setSubject("OTP for creating account with book my ticket");
			Context context = new Context();
			context.setVariable("name", name);
			context.setVariable("otp", otp);
			String text = templateEngine.process("email-template.html", context);
			helper.setText(text, true);
			javaMailSender.send(mimeMessage);
		}catch(Exception e) {
			System.err.println("Failed to send otp : " + otp);
		}
	}

}