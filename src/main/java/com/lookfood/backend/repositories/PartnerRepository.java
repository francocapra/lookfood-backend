package com.lookfood.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lookfood.backend.domain.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Integer> {
	
	@Transactional(readOnly=true)
	Partner findByEmail(String email);

}
