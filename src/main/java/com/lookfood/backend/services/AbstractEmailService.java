package com.lookfood.backend.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.lookfood.backend.domain.Review;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendReviewConfirmationEmail(Review obj) {
		// TODO Auto-generated method stub
		SimpleMailMessage smm = prepareSimpleMailMessageFromReview(obj);
		sendEmail(smm);
	}

	private SimpleMailMessage prepareSimpleMailMessageFromReview(Review obj) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(obj.getPartner().getEmail());
		smm.setFrom(sender);
		smm.setSubject("Avaliação confirmada! Código: " + obj.getId());
		smm.setSentDate(new Date(System.currentTimeMillis()));
		smm.setText(obj.toString());
		return smm;
	}
}
