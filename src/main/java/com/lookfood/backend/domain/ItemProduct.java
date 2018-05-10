package com.lookfood.backend.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemProduct implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@JsonIgnore	
	private ItemProductPK id = new ItemProductPK();
	
	private Integer rate;

	public ItemProduct() {
		super();
	}

	public ItemProduct(Review review, Product product, Integer rate) {
		super();
		this.id.setReview(review);
		this.id.setProduct(product);
		this.rate = rate;
	}
	
	public Product getProduct() {
		return id.getProduct();
	}
	
	public void setProduct(Product product) {
		id.setProduct(product);
	}

	public Integer getRate() {
		return rate;
	}
	
	public void setRate(Integer rate) {
		this.rate = rate;
	}

	@JsonIgnore
	public Review getReview(){
		return id.getReview();
	}
	
	public void setReview(Review review) {
		id.setReview(review);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemProduct other = (ItemProduct) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();		
		builder.append(getProduct().getDescription());
		builder.append(", Avaliação: ");
		builder.append(getRate());
		builder.append("\n");
		return builder.toString();
		
	}
	
	
}
