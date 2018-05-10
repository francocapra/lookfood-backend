package com.lookfood.backend.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lookfood.backend.domain.Professional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {
	
	@Transactional(readOnly=true)
	@Query(value= 
	"SELECT obj.id.professional , obj.rate " + 
		"FROM ItemProfessional obj " +
		"WHERE obj.id.review.partner.id = :partnerId " +
		"GROUP BY obj.id.professional.id " + 
		"ORDER BY AVG( obj.rate ) DESC " )
	List<Professional> findTop(@Param("partnerId") Integer partnerId, Pageable pageable);
}
