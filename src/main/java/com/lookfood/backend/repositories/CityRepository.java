package com.lookfood.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lookfood.backend.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

}
