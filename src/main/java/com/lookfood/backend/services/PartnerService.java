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
import com.lookfood.backend.repositories.PartnerRepository;
import com.lookfood.backend.services.exceptions.DataIntegrityException;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class PartnerService {
	
	@Autowired
	private PartnerRepository partnerRepository;
	
	public Partner find(Integer id) {
		Optional<Partner> obj = partnerRepository.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Partner.class.getName() ) ); 
		
	}
	
	public Partner insert(Partner obj) {
		obj.setId(null);
		return partnerRepository.save(obj);
	}

	public Partner update(Partner obj) {
		
		find(obj.getId());
		
		return partnerRepository.save(obj);
	}

	public void delete(Integer id) {
		
		this.find(id);		

		try {	partnerRepository.deleteById(id); } 
		
		catch (DataIntegrityViolationException e) {
			
			throw new DataIntegrityException("Não é possivel excluir um Partner que possui Reviews");
		}
	}
	
	public List<Partner> listAll() {
		
		return partnerRepository.findAll();
		
	}

	public Page<Partner> listPage(Integer page, Integer linesPerPage, Direction sortDirection, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, sortDirection, orderBy);
		return partnerRepository.findAll(pageRequest);
		
	}
	
}
