package com.lookfood.backend.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
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
	
	//Declaração de dependencia,(Mecanismo de Injeção de dependencia, ou inversão de controle)
	@Autowired //Intanciar automaticamente
	private PartnerService partnerService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Partner> find(@PathVariable Integer id) {
		
		Partner obj = partnerService.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Partner obj){
		
		obj = partnerService.insert(obj);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id,@RequestBody Partner obj) {
		
		obj.setId(id);
		obj = partnerService.update(obj); 
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		
		partnerService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PartnerDTO>> listAll() {
		
		List<Partner> list = partnerService.listAll();
		
		List<PartnerDTO> listDTO = list.stream().map(obj -> new PartnerDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}

	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<PartnerDTO>> listPage(
			@RequestParam(value="page"			, defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage"	, defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="sortDirection"	, defaultValue="ASC") Direction sortDirection, 
			@RequestParam(value="orderBy"		, defaultValue="name") String orderBy) {
		
		Page<Partner> list = partnerService.listPage(page, linesPerPage, sortDirection, orderBy);
		
		Page<PartnerDTO> listDTO = list.map(obj -> new PartnerDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
	}
}