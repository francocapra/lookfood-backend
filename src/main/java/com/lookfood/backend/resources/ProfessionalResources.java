package com.lookfood.backend.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lookfood.backend.domain.Professional;
import com.lookfood.backend.dto.ProfessionalDTO;
import com.lookfood.backend.services.ProfessionalService;

@RestController
@RequestMapping(value="/professionals")
public class ProfessionalResources {
	
	//Declaração de dependencia,(Mecanismo de Injeção de dependencia, ou inversão de controle)
	@Autowired //Intanciar automaticamente
	private ProfessionalService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Professional> find(@PathVariable Integer id) {
		
		Professional obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Professional obj){
		
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())				
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id,@RequestBody Professional obj) {
		
		obj.setId(id);
		obj = service.update(obj); 
		
		return ResponseEntity.noContent().build();
	} 
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ProfessionalDTO>> listAll() {
		
		List<Professional> list = service.listAll();
		
		List<ProfessionalDTO> listDTO = list.stream().map(obj -> new ProfessionalDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ProfessionalDTO>> listPage(
			@RequestParam(value="page"			, defaultValue="0" ) Integer page, 
			@RequestParam(value="linesPerPage"	, defaultValue="24" ) Integer linesPerPage, 
			@RequestParam(value="sortDirection"	, defaultValue="ASC" ) Direction sortDirection, 
			@RequestParam(value="orderBy"		, defaultValue="name" )String orderBy) {
		
		Page<Professional> list = service.findPage(page, linesPerPage, sortDirection, orderBy);
		
		Page<ProfessionalDTO> listDTO = list.map(obj -> new ProfessionalDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
	}
	
}
