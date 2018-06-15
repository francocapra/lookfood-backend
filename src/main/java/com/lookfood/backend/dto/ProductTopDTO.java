package com.lookfood.backend.dto;

import java.io.Serializable;

import com.lookfood.backend.domain.ItemProduct;

public class ProductTopDTO  implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String description;
	
	private Integer rate;
	
	private double price;
	
	private String origin;
	
	public ProductTopDTO() {
		super();
	}
		
	public ProductTopDTO(ItemProduct obj) {
		super();
		this.id = obj.getProduct().getId();
		this.description = obj.getProduct().getDescription();
		this.setRate(obj.getRate());
		this.price = obj.getProduct().getPrice();
		this.origin = obj.getProduct().getOrigin();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
