package com.lookfood.backend.dto;

import java.io.Serializable;

import com.lookfood.backend.domain.ItemProduct;

public class ProductTopDTO  implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String description;
	
	private Integer rate;
	
	public ProductTopDTO() {
		super();
	}
		
	public ProductTopDTO(ItemProduct obj) {
		super();
		this.id = obj.getProduct().getId();
		this.description = obj.getProduct().getDescription();
		this.setRate(obj.getRate());
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

}
