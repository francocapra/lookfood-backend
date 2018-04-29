package com.lookfood.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.Review;
import com.lookfood.backend.repositories.ReviewRepository;
import com.lookfood.backend.services.exceptions.DataIntegrityException;
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

		find(obj.getId());
		return reviewRepository.save(obj);
	}

	public void delete(Integer id) {

		find(id);
		try {
		reviewRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException
			("Não é possivel excluir Review pois existem Itens associados");
		}
	}


	public List<Review> listAll() {
		
		return reviewRepository.findAll();
		
	}
}
