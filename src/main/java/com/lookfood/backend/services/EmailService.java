package com.lookfood.backend.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.domain.Review;

public interface EmailService {
	
	void sendReviewConfirmationEmail(Review obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendReviewConfirmationHtmlEmail(Review obj); 
	
	void sendHtmlEmail(MimeMessage msg);

	void sendNewPasswordEmail(Partner partner, String newPass); 
}
