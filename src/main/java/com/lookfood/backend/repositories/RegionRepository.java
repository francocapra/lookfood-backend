package com.lookfood.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lookfood.backend.domain.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
	
	@Transactional(readOnly=true)
	public List<Region> findAllByOrderByName(); 
	
}
