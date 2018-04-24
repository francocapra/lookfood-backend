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
	
	public Product fetchReview(Integer id) {
		Optional<Product> obj = productRepository.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName() )); 
		
	}
	
}
