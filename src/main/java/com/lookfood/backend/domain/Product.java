package com.lookfood.backend.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String description;
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")	
	private Date date;
	
	@ManyToMany(mappedBy = "products")
	private List<Professional> professionals = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="id.review")
	private Set<ItemProduct> itensProduct = new HashSet<>();
	
	public Product() {
		super();
	}

	public Product(Integer id, String description, Date date) {
		super();
		this.id = id;
		this.description = description;
		this.date = date;
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

	public List<Professional> getProfessionals() {
		return professionals;
	}

	public void setProfessionals(List<Professional> professionals) {
		this.professionals = professionals;
	}

	public Set<ItemProduct> getItensProduct() {
		return itensProduct;
	}

	public void setItensProduct(Set<ItemProduct> itensProduct) {
		this.itensProduct = itensProduct;
	}

	@JsonIgnore //Tudo que começa com GET é serializado, então eu não quero serealizar esta lista de pedidos
	public List<Review> getReviews(){
		List<Review> listReview = new ArrayList<>();
		for (ItemProduct x : getItensProduct() ) {
			listReview.add(x.getReview());
		}
		return listReview;
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
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
