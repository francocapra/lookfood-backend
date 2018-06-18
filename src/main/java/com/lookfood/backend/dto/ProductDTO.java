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
	
	private String currency;
	
	private String idExternal;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private String fromCountry;
	
	public ProductDTO() {
		super();
	}
		
	public ProductDTO(Product obj) {
		super();
		this.id = obj.getId();
		this.description = obj.getDescription();
		this.price = obj.getPrice();
		this.currency = obj.getCurrency();
		this.idExternal = obj.getIdExternal();
		this.fromCountry = obj.getFromCountry();
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getFromCountry() {
		return fromCountry;
	}

	public void setFromCountry(String fromCountry) {
		this.fromCountry = fromCountry;
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

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


}
