package com.lookfood.backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.repositories.PartnerRepository;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class PartnerService {
	
	@Autowired
	private PartnerRepository partnerRepository;
	
	public Partner fetchPartner(Integer id) {
		Optional<Partner> obj = partnerRepository.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Partner.class.getName() )); 
		
	}
	
	
}
