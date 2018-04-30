package com.lookfood.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lookfood.backend.domain.Product;
import com.lookfood.backend.domain.Professional;
import com.lookfood.backend.dto.ProductDTO;
import com.lookfood.backend.repositories.ProductRepository;
import com.lookfood.backend.repositories.ProfessionalRepository;
import com.lookfood.backend.services.exceptions.DataIntegrityException;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private ProfessionalRepository professionalRepository;
	
	public Product find(Integer id) {		
		Optional<Product> obj = repository.findById(id);			
		return obj.orElseThrow( () -> new ObjectNotFoundException
				("Objeto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName() )); 		
	
	}
	
	public Page<Product> search(
			String description, 
			List<Integer> ids,
			Integer page, 
			Integer linesPerPage, 
			String orderBy, 
			String sortDirection){
		
		PageRequest pageRequest = PageRequest.of(
				page, 
				linesPerPage, 
				Direction.valueOf(sortDirection) , 
				orderBy);
		
		List<Professional> professionals = professionalRepository.findAllById(ids);
		
		return repository.findDistinctByDescriptionContainingAndProfessionalsIn(description, professionals, pageRequest);
	}
	
	@Transactional
	public Product insert(Product obj) {
		obj.setId(null);
		return repository.save(obj);		
	}

	public Product update(Product obj) {
		Product newObj = find(obj.getId());
		updateData( newObj, obj);
		return repository.save(obj);
	}
	
	private void updateData(Product newObj, Product obj) {
		newObj.setDescription(obj.getDescription());
		newObj.setDate(obj.getDate());
	}

	public void delete(Integer id) {
		this.find(id);		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException
			("Não é possivel excluir um Product que possui um Professional ");
		}
	}
	
	public List<Product> findAll() {		
		return repository.findAll();		
	}
	
	public Page<Product> findPage(
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
	
	public Product fromDTO(ProductDTO objDTO) {
		return new Product(
				objDTO.getId(), 
				objDTO.getDescription(), 
				objDTO.getDate());
	}
	
}
