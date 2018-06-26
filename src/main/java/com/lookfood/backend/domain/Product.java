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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Product implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String description;
	
	private Double price;
	
	private String currency;
	
	private String idExternal;
	
	private String countryIsoCode;
	
	private Integer category;
	
	private String fullDescription;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")	
	private Date createdDate;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")	
	private Date modifiedDate;		

	@ManyToOne
	@JoinColumn(name="partner_id")
	@JsonIgnoreProperties({	
		"cnpj", 
		"website",
		"phones",
		"profiles",
		"addresses", 
		"reviews", 
		"products",
		"professionals"})
	private Partner partner;
	
	@ManyToMany(mappedBy = "products")
	private List<Professional> professionals = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="id.review")
	private Set<ItemProduct> itensProduct = new HashSet<>();
	
	public Product() {
		super();
	}

	public Product(Integer id, String description, Double price, String currency, String idExternal, String countryIsoCode, Integer category, String fullDescription ) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
		this.currency = currency;
		this.idExternal = idExternal;
		this.countryIsoCode = countryIsoCode;
		this.category = category;
		this.fullDescription = fullDescription;
	}
	

	@JsonIgnore //Tudo que começa com GET é serializado, então eu não quero serealizar esta lista de pedidos
	public List<Review> getReviews(){
		List<Review> listReview = new ArrayList<>();
		for (ItemProduct x : itensProduct ) {
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

	public String getCountryIsoCode() {
		return countryIsoCode;
	}

	public void setCountryIsoCode(String countryIsoCode) {
		this.countryIsoCode = countryIsoCode;
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
	
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
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

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
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
