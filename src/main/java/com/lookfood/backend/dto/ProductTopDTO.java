package com.lookfood.backend.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.lookfood.backend.domain.ItemProduct;
import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.domain.enums.TypeCountries;
import com.lookfood.backend.domain.enums.TypeProductCategories;

public class ProductTopDTO  implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String description;
	
	private Integer rate;
	
	private double price;
	
	private String countryIsoCode;
	
	private String fromCountryName;
	
	private Integer category;
	
	private String categoryName;

	private long numberOfReviews;
	
	@Length(max=500, message="O tamanho máximo não pode ultrapassar 500 caracteres")
	private String fullDescription;
	
	private Partner partner;
	
	public ProductTopDTO() {
		super();
	}
		
	public ProductTopDTO(Integer id, String description, Integer rate, double price, String countryIsoCode,
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

	public ProductTopDTO(ItemProduct obj) {
		super();
		this.id = obj.getProduct().getId();
		this.description = obj.getProduct().getDescription();
		this.setRate(obj.getRate());
		this.price = obj.getProduct().getPrice();
		this.countryIsoCode = obj.getProduct().getCountryIsoCode();
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

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getCountryIsoCode() {
		return countryIsoCode;
	}

	public void setCountryIsoCode(String countryIsoCode) {
		this.countryIsoCode = countryIsoCode;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

}
