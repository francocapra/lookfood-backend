package com.lookfood.backend.resources;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.security.Provider.Service;

import org.apache.catalina.valves.rewrite.Substitution.ServerVariableHttpElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lookfood.backend.domain.Review;
import com.lookfood.backend.services.ReviewService;

@RestController
@RequestMapping(value="/reviews")
public class ReviewResources {
	
	@Autowired 
	private ReviewService reviewService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Review obj = reviewService.fetchReview(id);
		return ResponseEntity
				.ok()
				.body(obj);
		
	}
	
//	Receber uma dominio no formato Json, e inserir no banco de dados
//	ResponseEntity = "Resposta HTTP"
//	Void === Não vai ter corpo, Quando eu inserir um "Corpo(Body)" com sucesso! ele(method) 
//	vou retornar uma reposta com o corpo vazio!!
	
	public ResponseEntity<Void>  insert(Review obj){
		
		obj = this.reviewService.insertReview(obj);	
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		
		 return ResponseEntity.created(uri).build();
	}
	
}
