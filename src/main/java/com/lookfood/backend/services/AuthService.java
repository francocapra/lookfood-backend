package com.lookfood.backend.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.repositories.PartnerRepository;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private PartnerRepository partnerRepository;
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		
		Partner partner = partnerRepository.findByEmail(email);
		if (partner==null) {
			throw new ObjectNotFoundException("Email não encontrado");			
		}
		
		String newPass = newPassword();
		
		partner.setPassword(passwordEncoder.encode(newPass));
		
		partnerRepository.save(partner);
		
		emailService.sendNewPasswordEmail(partner, newPass);
		
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++ ) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		if (opt == 0) { //gera um digito
			return (char) (random.nextInt(10) + 48);
		}else if (opt == 1 ) { //gera letra maiscula			
			return (char) (random.nextInt(26) + 65);
		}else { //gera letra minuscula
			return (char) (random.nextInt(26) + 97);
		}
	}
}
