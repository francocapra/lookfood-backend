package com.lookfood.backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.Product;
import com.lookfood.backend.repositories.ProductRepository;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product find(Integer id) {
		Optional<Product> obj = productRepository.findById(id);
		return obj.orElseThrow( () 
		-> new ObjectNotFoundException("Objeto não encontrado! Id: " 
										+ id 
										+ ", Tipo: " 
										+ Product.class.getName() )); 		
	}
	
	public Product insert(Product obj) {
		obj.setId(null);
		return productRepository.save(obj);		
	}

	public Product update(Product obj) {
		// TODO Auto-generated method stub
		find(obj.getId());
		return productRepository.save(obj);
	}
	
}
