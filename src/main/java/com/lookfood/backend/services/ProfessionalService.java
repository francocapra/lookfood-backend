package com.lookfood.backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.Professional;
import com.lookfood.backend.repositories.ProfessionalRepository;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class ProfessionalService {
	
	@Autowired
	private ProfessionalRepository professionalRepository;
	
	public Professional fetchReview(Integer id) {
		Optional<Professional> obj = professionalRepository.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Professional.class.getName() )); 
		
	}
	
}
