package com.lookfood.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.repositories.PartnerRepository;
import com.lookfood.backend.security.UserSS;

@Service
public class UserSSService implements UserDetailsService{
	
	@Autowired
	private PartnerRepository partnerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Partner partner = partnerRepository.findByEmail(email);
		
		if (partner == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserSS(partner.getId(),partner.getEmail(),partner.getPassword(),partner.getProfiles());
	}

}
