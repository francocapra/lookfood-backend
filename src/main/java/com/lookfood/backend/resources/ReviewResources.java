package com.lookfood.backend.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lookfood.backend.domain.Review;
import com.lookfood.backend.dto.ReviewDTO;
import com.lookfood.backend.services.ReviewService;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewResources {

	@Autowired
	private ReviewService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Review> find(@PathVariable Integer id) {
		
		Review obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
	}

	// Receber uma dominio no formato Json, e inserir no banco de dados
	// ResponseEntity = "Resposta HTTP"
	// Void === Não vai ter corpo, Quando eu inserir um "Corpo(Body)" com sucesso!
	// ele(method)
	// vou retornar uma reposta com o corpo vazio!!
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Review obj) {

		obj = this.service.insert(obj);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody Review obj) {

		obj.setId(id);
		obj = service.update(obj);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		service.delete(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ReviewDTO>> listAll() {
		
		List<Review> list = service.listAll();
		
		List<ReviewDTO> listDTO = list.stream().map(obj -> new ReviewDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	
}
