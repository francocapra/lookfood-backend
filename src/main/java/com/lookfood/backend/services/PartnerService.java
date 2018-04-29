package com.lookfood.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.dto.PartnerDTO;
import com.lookfood.backend.repositories.PartnerRepository;
import com.lookfood.backend.services.exceptions.DataIntegrityException;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class PartnerService {
	
	@Autowired
	private PartnerRepository repository;
	
	public Partner find(Integer id) {		
		Optional<Partner> obj = repository.findById(id);				
		return obj.orElseThrow( () -> new ObjectNotFoundException
				("Objeto não encontrado! Id: " + id + ", Tipo: " + Partner.class.getName() )); 		
	}
	
	public Partner insert(Partner obj) {
		obj.setId(null);
		return repository.save(obj);		
	}

	public Partner update(Partner obj) {		
		Partner newObj = find(obj.getId());		
		updateData(newObj,obj);
		return repository.save(newObj);				
	}
	
	private void updateData(Partner newObj, Partner obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
		newObj.setWebsite(obj.getWebsite());
	}

	public void delete(Integer id) {

		this.find(id);		
		
		try { repository.deleteById(id);} 
		
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException
			("Não é possivel excluir um Partner porque há entidades relacionadas");		
		}
	}
	
	public List<Partner> listAll() {		
		return repository.findAll();	
	}
	
	public Page<Partner> listPage(
			Integer page, 
			Integer linesPerPage, 
			String orderBy, 
			String sortDirection){
		
		PageRequest pageRequest = PageRequest.of(
				page, 
				linesPerPage, 
				Direction.valueOf(sortDirection) , 
				orderBy);
		
		return repository.findAll(pageRequest);
		
	}
	
	public Partner fromDTO(PartnerDTO objDTO) {
		return new Partner(
				objDTO.getId(), 
				objDTO.getName(), 
				objDTO.getEmail(), 
				null, 
				objDTO.getWebsite());
	}
	
}
