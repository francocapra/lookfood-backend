package com.lookfood.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lookfood.backend.domain.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

}
