package com.lookfood.backend.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.lookfood.backend.domain.ItemProduct;

public class ItemReviewDTO  implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5,max=80,message="O tamanho deve ser entre 5 a 80 caracteres")
	private String description;
	
	private Double price;
	
	private String currency;
	
	private Integer rate;
	
	private String idExternal;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	public ItemReviewDTO() {
		super();
	}
		
	public ItemReviewDTO(ItemProduct obj) {
		super();
		this.id = obj.getProduct().getId();
		this.description = obj.getProduct().getDescription();
		this.price = obj.getProduct().getPrice();
		this.currency = obj.getProduct().getCurrency();
		this.idExternal = obj.getProduct().getIdExternal();
		this.createdDate = obj.getProduct().getCreatedDate();
		this.modifiedDate = obj.getProduct().getModifiedDate();
		this.rate = obj.getRate();
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

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
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
