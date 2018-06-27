package com.lookfood.backend.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.domain.Product;
import com.lookfood.backend.domain.enums.TypeCountries;
import com.lookfood.backend.domain.enums.TypeProductCategories;

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
	
	private String countryIsoCode;
	
	private Integer category;
	
	@Length(max=500, message="O tamanho deve ser no máximo 500 caracteres")
	private String fullDescription;

	private Integer rate;

	private long numberOfReviews;

	private String fromCountryName;

	private String categoryName;
	
	private Partner partner;

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
		this.countryIsoCode = obj.getCountryIsoCode();
		this.category = obj.getCategory();
	}
	
	public ProductDTO(Integer id, String description, Integer rate, double price, String countryIsoCode,
			long numberOfReviews, Integer category, String fullDescription, Partner partner) {
		super();
		this.id = id;
		this.description = description;
		this.rate = rate;
		this.price = price;
		this.countryIsoCode = countryIsoCode;
		this.numberOfReviews = numberOfReviews;
		this.fromCountryName = TypeCountries.toEnum(countryIsoCode).getDescription();
		this.category = category;
		this.categoryName = TypeProductCategories.toEnum(category).getDescription();
		this.fullDescription = fullDescription;
		this.partner = partner;
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

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public long getNumberOfReviews() {
		return numberOfReviews;
	}

	public void setNumberOfReviews(long numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}

	public String getFromCountryName() {
		return fromCountryName;
	}

	public void setFromCountryName(String fromCountryName) {
		this.fromCountryName = fromCountryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

}
