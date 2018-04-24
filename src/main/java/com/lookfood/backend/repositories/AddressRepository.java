package com.lookfood.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lookfood.backend.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
