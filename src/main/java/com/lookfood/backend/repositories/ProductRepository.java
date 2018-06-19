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
import com.lookfood.backend.dto.ProductTopDTO;

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
	
	public static final String BASIC_TOP_PRODUCT_DTO = "SELECT new com.lookfood.backend.dto.ProductTopDTO(obj.id.product.id, obj.id.product.description, obj.rate, "
			+ "obj.id.product.price, obj.id.product.countryIsoCode, count(obj.id.product.id), obj.id.product.category) ";
	
	@Transactional(readOnly=true)
	Page<Product> findDistinctByDescriptionContainingAndProfessionalsIn(
			String description, 
			List<Professional> professionals, 
			Pageable pageRequest);
	
	@Transactional(readOnly=true)
	@Query(value=BASIC_TOP_PRODUCT_DTO +
		"FROM ItemProduct obj " +
		"GROUP BY obj.id.product.id " + 
		"ORDER BY AVG( obj.rate ) DESC, count(obj.id.product.id) DESC" )
	List<ProductTopDTO> findTop(Pageable pageable);
	
	@Transactional(readOnly=true)
	@Query(value=BASIC_TOP_PRODUCT_DTO
			+ "FROM ItemProduct obj "
			+ "WHERE obj.id.product.price <= 50.00 "
			+ "GROUP BY obj.id.product.id "
			+ "ORDER BY AVG( obj.rate ) DESC, count(obj.id.product.id) DESC")
	List<ProductTopDTO> findTopUpToFifty(Pageable pageable);
	
	@Transactional(readOnly=true)
	@Query(value=BASIC_TOP_PRODUCT_DTO
			+ "FROM ItemProduct obj "
			+ "WHERE obj.id.product.countryIsoCode = :countryIsoCode "
			+ "GROUP BY obj.id.product.id "
			+ "ORDER BY AVG( obj.rate ) DESC, count(obj.id.product.id) DESC")
	List<ProductTopDTO> findByCountry(Pageable pageable, @Param(value="countryIsoCode") String countryIsoCode);
	
	@Transactional
	@Query(value=BASIC_TOP_PRODUCT_DTO
			+ "FROM ItemProduct obj "
			+ "WHERE obj.id.product.category = :categoryCode "
			+ "GROUP BY obj.id.product.id "
			+ "ORDER BY AVG( obj.rate ) DESC, count(obj.id.product.id) DESC")
	List<ProductTopDTO> findByCategory(Pageable pageable, @Param(value="categoryCode") Integer categoryCode);
	
	@Transactional(readOnly=true)
	List<Product> findAllByPartnerId(Integer partnerId);
	
}
