package com.lookfood.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	@Transactional(readOnly=true)
	Page<Review> findByPartner(Partner partner, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	Review findByReviewCode(String reviewCode);
	
}
