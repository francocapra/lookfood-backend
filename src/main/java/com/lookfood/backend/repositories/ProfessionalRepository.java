package com.lookfood.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lookfood.backend.domain.Professional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {

}
