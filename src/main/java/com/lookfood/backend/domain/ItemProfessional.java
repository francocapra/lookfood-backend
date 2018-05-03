package com.lookfood.backend.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemProfessional implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@JsonIgnore	
	private ItemProfessionalPK id = new ItemProfessionalPK();
	
	private Integer rate;

	public ItemProfessional() {
		super();
	}

	public ItemProfessional(Review review, Professional professional, Integer rate) {
		super();
		this.id.setReview(review);
		this.id.setProfessional(professional);
		this.rate = rate;
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
	
	public Professional getProfessional() {
		return id.getProfessional();
	}
	
	public void setProfessional(Professional professional) {
		id.setProfessional(professional);
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
		ItemProfessional other = (ItemProfessional) obj;
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
		builder.append(getProfessional().getPosition().getDescription());
		builder.append(", ");
		builder.append(getProfessional().getName());
		builder.append(", Avaliação: ");
		builder.append(getRate());
		builder.append("\n");
		return builder.toString();
	}
}
