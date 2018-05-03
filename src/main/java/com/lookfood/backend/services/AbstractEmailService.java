package com.lookfood.backend.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.lookfood.backend.domain.Review;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendReviewConfirmationEmail(Review obj) {
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

	protected String htmlFromTemplateReview(Review obj) {
		Context context = new Context();
		context.setVariable("review", obj);
		return templateEngine.process("email/ReviewConfirmation", context);
	}
	
	@Override
	public void sendReviewConfirmationHtmlEmail(Review obj) {

		try {
			MimeMessage mm = prepareMimeMessageFromReview(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			e.printStackTrace();
			sendReviewConfirmationEmail(obj);
		}

	}

	private MimeMessage prepareMimeMessageFromReview(Review obj) throws MessagingException {
		MimeMessage mm = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
		mmh.setTo(obj.getPartner().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Avaliação confirmada! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplateReview(obj), true);

		return mm;
	}
}
