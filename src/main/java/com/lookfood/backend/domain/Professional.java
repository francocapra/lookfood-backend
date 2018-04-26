package com.lookfood.backend.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lookfood.backend.domain.enums.TypePosition;

@Entity
public class Professional implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Integer position;
	
	@ManyToMany
	@JsonIgnore
	@JoinTable(	name				="PROFESSIONAL_PRODUCT",
				joinColumns			=@JoinColumn(name="professional_id"),
				inverseJoinColumns	=@JoinColumn(name="product_id"))
	private List<Product> products = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="id.review")
	private Set<ItemProfessional> itensProfessional = new HashSet<>();
	
	public Professional() {
		super();
	}

	public Professional(Integer id, String name, TypePosition position) {
		super();
		this.id = id;
		this.name = name;
		this.position = position.getCod();
	}

	@JsonIgnore 
	public List<Review> getReviews(){
		List<Review> listReview = new ArrayList<>();
		for (ItemProfessional x : itensProfessional ) {
			listReview.add(x.getReview());
		}
		return listReview;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypePosition getPosition() {
		return TypePosition.toEnum(position);
	}

	public void setPosition(TypePosition position) {
		this.position = position.getCod();
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Set<ItemProfessional> getItensProfessional() {
		return itensProfessional;
	}

	public void setItensProfessional(Set<ItemProfessional> itensProfessional) {
		this.itensProfessional = itensProfessional;
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
		Professional other = (Professional) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
