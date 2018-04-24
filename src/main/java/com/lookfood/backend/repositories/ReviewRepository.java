package com.lookfood.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lookfood.backend.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
