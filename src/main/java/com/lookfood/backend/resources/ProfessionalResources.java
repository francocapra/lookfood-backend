package com.lookfood.backend.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lookfood.backend.domain.Professional;
import com.lookfood.backend.services.ProfessionalService;

@RestController
@RequestMapping(value="/professionals")
public class ProfessionalResources {
	
	//Declaração de dependencia,(Mecanismo de Injeção de dependencia, ou inversão de controle)
	@Autowired //Intanciar automaticamente
	private ProfessionalService professionalService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Professional> find(@PathVariable Integer id) {
		
		Professional obj = professionalService.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Professional obj){
		
		obj = professionalService.insert(obj);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())				
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id,@RequestBody Professional obj) {
		
		obj.setId(id);
		obj = professionalService.update(obj); 
		
		return ResponseEntity.noContent().build();
	} 
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		
		professionalService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Professional>> listAll() {
		
		List<Professional> list = professionalService.listAll();
		
		return ResponseEntity.ok().body(list);
	}
	
}
