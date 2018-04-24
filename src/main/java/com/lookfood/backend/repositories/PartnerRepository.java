package com.lookfood.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lookfood.backend.domain.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Integer> {

}
