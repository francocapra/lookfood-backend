package com.lookfood.backend.resources;

import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ReviewDTO> findByReviewCode(
			@RequestParam(value = "code") String reviewCode) {

		ReviewDTO objDTO = service.findByCode(reviewCode);	
		
		return ResponseEntity.ok().body(objDTO);		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody ReviewDTO objDTO) {

		Review newObj = this.service.insert(objDTO);		
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}/{reviewCode}")
					.buildAndExpand(newObj.getId(),newObj.getReviewCode()).toUri();		
		
		return ResponseEntity.created(uri).build();

	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<Review>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "sortDirection", defaultValue = "DESC") Direction sortDirection,
			@RequestParam(value = "orderBy", defaultValue = "date") String orderBy) {

		Page<Review> list = service.findPage(page, linesPerPage, sortDirection, orderBy);

		return ResponseEntity.ok().body(list);
	}
}
