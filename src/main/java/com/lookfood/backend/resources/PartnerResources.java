package com.lookfood.backend.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.dto.PartnerDTO;
import com.lookfood.backend.services.PartnerService;

@RestController
@RequestMapping(value="/partners")
public class PartnerResources {
		
	@Autowired 
	private PartnerService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Partner> find(@PathVariable Integer id) {
		
		Partner obj = service.find(id);
		return ResponseEntity.ok().body(obj);

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(
			@Valid @RequestBody PartnerDTO objDTO) {
		
		Partner obj = service.fromDTO(objDTO);
		obj = this.service.insert(obj);		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(			
			@PathVariable Integer id, 
			@Valid @RequestBody PartnerDTO objDTO) {	
		
		Partner obj = service.fromDTO(objDTO);		
		obj.setId(id);		
		obj = service.update(obj);		
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PartnerDTO>> listAll() {

		// Recebo a LISTA de Produtos, e para cada elemnto desta lista eu vou instanciar
		// um DTO
		List<Partner> list = service.listAll();

		// Percorrer objeto LIST, usando STREAM (Recurso do Java 8),
		// Operação, MAP(vai efetuar uma operação para cada elemento)
		// Apelido, "obj" cada elemento da lista eu posso dar um apelido
		// Função, "->" ARROW executar uma função em cada elemento da lista
		// Criar, "new" Criar um novo objeto DTO
		// Collect, voltar o STREAM de objetos para o tipo LIST, usando COLLECTOR
		List<PartnerDTO> listDTO = 
				list.stream()
				.map(obj -> new PartnerDTO(obj))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(listDTO);
	}

	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<PartnerDTO>> listPage(
			@RequestParam(value="page"			, defaultValue="0" ) Integer page, 
			@RequestParam(value="linesPerPage"	, defaultValue="24" ) Integer linesPerPage, 
			@RequestParam(value="orderBy"		, defaultValue="name" ) String orderBy, 
			@RequestParam(value="sortDirection"	, defaultValue="ASC" ) String sortDirection) {
		
		Page<Partner> list = service.listPage(
				page, 
				linesPerPage, 
				orderBy, 
				sortDirection);
		
//		PAGE(é java complice, JAVA 8 ), não precisa de STREAM, nem de Collect
		Page<PartnerDTO> listDTO = list.map(obj -> new PartnerDTO(obj));		
		return ResponseEntity.ok().body(listDTO);
		
	}
}