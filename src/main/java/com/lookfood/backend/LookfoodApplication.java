package com.lookfood.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lookfood.backend.services.S3Service;

@SpringBootApplication
public class LookfoodApplication implements CommandLineRunner{	
	
	@Autowired
	private S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(LookfoodApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\Users\\c5260504\\OneDrive\\Documents\\_WORKSPACE\\_MyProjects\\LookFood-Documentos\\Lookfood-version1-Class Diagram.jpg");
	}
}
