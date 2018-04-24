package com.lookfood.backend.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lookfood.backend.domain.Professional;
import com.lookfood.backend.services.ProfessionalService;

@RestController
@RequestMapping(value="/professionals")
public class ProfessionalResources {
	
	//Declaração de dependencia,(Mecanismo de Injeção de dependencia, ou inversão de controle)
	@Autowired //Intanciar automaticamente
	private ProfessionalService professionalService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Professional obj = professionalService.fetchReview(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
}
