package com.lookfood.backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.Review;
import com.lookfood.backend.repositories.ReviewRepository;
import com.lookfood.backend.services.exceptions.ObjectNotFoundException;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	public Review find(Integer id) {
		Optional<Review> obj = reviewRepository.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Review.class.getName() )); 
		
	}
	
	public Review insert(Review obj) {
		obj.setId(null);
		return reviewRepository.save(obj);
	}

	public Review update(Review obj) {
		// TODO Auto-generated method stub
		find(obj.getId());
		return reviewRepository.save(obj);
	}
}
