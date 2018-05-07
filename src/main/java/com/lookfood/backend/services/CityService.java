package com.lookfood.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lookfood.backend.domain.City;
import com.lookfood.backend.repositories.CityRepository;

@Service
public class CityService {
	
	@Autowired
	private CityRepository repository;
	
	public List<City> findByRegion(Integer regionId) {
		return repository.findCities(regionId);
	}
}
