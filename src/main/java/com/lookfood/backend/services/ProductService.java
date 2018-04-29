package com.lookfood.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.Product;
import com.lookfood.backend.dto.ProductDTO;
import com.lookfood.backend.repositories.ProductRepository;
import com.lookfood.backend.services.exceptions.DataIntegrityException;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	public Product find(Integer id) {
		
		Optional<Product> obj = repository.findById(id);			
		return obj.orElseThrow( () -> new ObjectNotFoundException
				("Objeto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName() )); 		
	}
	
	public Product insert(Product obj) {
		obj.setId(null);
		return repository.save(obj);		
	}

	public Product update(Product obj) {
		// TODO Auto-generated method stub
		find(obj.getId());
		return repository.save(obj);
	}
	
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		this.find(id);		
		try {
		repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityException("Não é possivel excluir um Product que possui um Professional ");
		}
	}
	
	public List<Product> listAll() {
		
		return repository.findAll();
		
	}
	
	public Page<Product> listPage(Integer page, Integer linesPerPage, String orderBy, String sortDirection){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(sortDirection) , orderBy);
		
		return repository.findAll(pageRequest);
		
	}
	
	public Product fromDTO(ProductDTO objDTO) {
		return new Product(objDTO.getId(), objDTO.getDescription(), objDTO.getDate());
	}
	
}
