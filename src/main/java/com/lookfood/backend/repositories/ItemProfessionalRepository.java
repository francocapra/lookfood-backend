package com.lookfood.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lookfood.backend.domain.ItemProfessional;

@Repository
public interface ItemProfessionalRepository extends JpaRepository<ItemProfessional, Integer> {

}
