package com.lookfood.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.Professional;
import com.lookfood.backend.repositories.ProfessionalRepository;
import com.lookfood.backend.services.exceptions.DataIntegrityException;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class ProfessionalService {
	
	@Autowired
	private ProfessionalRepository professionalRepository;
	
	public Professional find(Integer id) {
		Optional<Professional> obj = professionalRepository.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Professional.class.getName() )); 
		
	}
	
	public Professional insert(Professional obj) {
		obj.setId(null);
		return professionalRepository.save(obj);
	}

	public Professional update(Professional obj) {
		// TODO Auto-generated method stub
		find(obj.getId());
		return professionalRepository.save(obj);
	}

	public void delete(Integer id) {
		// TODO Auto-generated method stub
		find(id);
		try {
		professionalRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityException("Não é possivel excluir uma Professional que possui Products");
		}		
	}
	
	public List<Professional> listAll() {
		
		return professionalRepository.findAll();
		
	}
	
	public Page<Professional> listPage(Integer page, Integer linesPerPage, Direction sortDirection, String orderBy ) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, sortDirection, orderBy);
		return professionalRepository.findAll(pageRequest);
	}
	
	
}
