package com.lookfood.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lookfood.backend.services.DBService;
import com.lookfood.backend.services.EmailService;
import com.lookfood.backend.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDataBase() throws Exception {
		
		if (!"create".equals(strategy)) {
			return false;
		}
		
		dbService.instatiateTestDataBase();
		return true;
	}
	
	@Bean
	public EmailService emailservice() {
		return new SmtpEmailService();
	}
}
