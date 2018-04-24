package com.lookfood.backend.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lookfood.backend.domain.Product;
import com.lookfood.backend.services.ProductService;

@RestController
@RequestMapping(value="/products")
public class ProductResources {
	
	//Declaração de dependencia,(Mecanismo de Injeção de dependencia, ou inversão de controle)
	@Autowired //Intanciar automaticamente
	private ProductService productService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Product obj = productService.fetchReview(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
}
