package com.lookfood.backend;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lookfood.backend.domain.Review;
import com.lookfood.backend.domain.enums.TypeStatus;
import com.lookfood.backend.repositories.ReviewRepository;

@SpringBootApplication
public class LookfoodApplication implements CommandLineRunner{
	
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(LookfoodApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
//		Review r1 = new Review(null, TypeStatus.ACTIVE, sdf.parse("20/10/2017 00:00") , 10 );
		
//		reviewRepository.saveAll(Arrays.asList(r1));
	}
}
