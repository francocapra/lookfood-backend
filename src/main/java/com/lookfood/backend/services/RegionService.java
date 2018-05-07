package com.lookfood.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.Region;
import com.lookfood.backend.repositories.RegionRepository;

@Service
public class RegionService {

	@Autowired
	private RegionRepository repository;
	
	public List<Region> findAll() {
		return repository.findAllByOrderByName();
	}
}
