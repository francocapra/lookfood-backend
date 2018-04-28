package com.lookfood.backend.dto;

import java.io.Serializable;
import java.util.Date;

import com.lookfood.backend.domain.Product;

public class ProductDTO  implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String description;
	private Date date;
	
	public ProductDTO() {
		super();
	}
	
	
	
	public ProductDTO(Product obj) {
		super();
		this.id = obj.getId();
		this.description = obj.getDescription();
		this.date = obj.getDate();
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
