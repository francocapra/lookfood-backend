package com.lookfood.backend.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lookfood.backend.domain.ItemProduct;
import com.lookfood.backend.domain.Product;
import com.lookfood.backend.domain.Professional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
//	@Query("SELECT DISTINCT obj 
//			FROM Product obj INNER JOIN obj.professionals prof 
//			WHERE obj.description LIKE %:description% AND prof IN :professionals")
//	Page<Product> search(
//			@Param("description") String description, 
//			@Param("professionals") List<Professional> professionals, 
//			Pageable pageRequest);

//FONT: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	
	@Transactional(readOnly=true)
	Page<Product> findDistinctByDescriptionContainingAndProfessionalsIn(
			String description, 
			List<Professional> professionals, 
			Pageable pageRequest);
	
	@Transactional(readOnly=true)
	@Query(value= 
	"SELECT obj " + 
		"FROM ItemProduct obj " +
		"GROUP BY obj.id.product.id, obj.id.review.id " + 
		"ORDER BY AVG( obj.rate ) DESC " )
	List<ItemProduct> findTop(Pageable pageable);
	
	@Transactional(readOnly=true)
	List<Product> findAllByPartnerId(Integer partnerId);
	
}
