package com.lookfood.backend.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.lookfood.backend.domain.Product;

public class ProductDTO  implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5,max=80,message="O tamanho deve ser entre 5 a 80 caracteres")
	private String description;
	
	private Double price;
	
	private String idExternal;
	
	private Date createdDate;
	
	public ProductDTO() {
		super();
	}
		
	public ProductDTO(Product obj) {
		super();
		this.id = obj.getId();
		this.description = obj.getDescription();
		this.createdDate = obj.getCreatedDate();
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getIdExternal() {
		return idExternal;
	}

	public void setIdExternal(String idExternal) {
		this.idExternal = idExternal;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date date) {
		this.createdDate = date;
	}
	
	
	
}
