package com.lookfood.backend.services;

import org.springframework.mail.SimpleMailMessage;

import com.lookfood.backend.domain.Review;

public interface EmailService {
	
	void sendReviewConfirmationEmail(Review obj);
	
	void sendEmail(SimpleMailMessage msg);
}
