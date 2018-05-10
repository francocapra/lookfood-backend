package com.lookfood.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lookfood.backend.domain.ItemProduct;

@Repository
public interface ItemProductRepository extends JpaRepository<ItemProduct, Integer> {


}
