package com.lookfood.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lookfood.backend.services.EmailService;
import com.lookfood.backend.services.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {

	@Bean
	public EmailService emailservice() {
		return new SmtpEmailService();
	}

}
