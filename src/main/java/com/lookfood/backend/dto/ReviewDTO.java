package com.lookfood.backend.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lookfood.backend.domain.Review;

public class ReviewDTO implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String status;
	
	private List<ProductDTO> products = new ArrayList<>();
	
	public ReviewDTO() {
		super();
	}	
	
	public ReviewDTO(Review obj) {
		super();
		this.id = obj.getId();
		this.status = obj.getStatus().getDescription();
	}	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}	
}
