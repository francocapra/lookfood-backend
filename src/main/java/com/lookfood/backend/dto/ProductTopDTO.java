package com.lookfood.backend.dto;

import java.io.Serializable;

import com.lookfood.backend.domain.ItemProduct;
import com.lookfood.backend.domain.enums.TypeCountries;
import com.lookfood.backend.domain.enums.TypeProductCategories;

public class ProductTopDTO  implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String description;
	
	private Integer rate;
	
	private double price;
	
	private String fromCountry;
	
	private String fromCountryName;
	
	private Integer category;
	
	private String categoryName;

	private long numberOfReviews;
	
	public ProductTopDTO() {
		super();
	}
		
	public ProductTopDTO(Integer id, String description, Integer rate, double price, String fromCountry,
			long numberOfReviews, Integer category) {
		super();
		this.id = id;
		this.description = description;
		this.rate = rate;
		this.price = price;
		this.fromCountry = fromCountry;
		this.numberOfReviews = numberOfReviews;
		this.fromCountryName = TypeCountries.toEnum(fromCountry).getDescription();
		this.category = category;
		this.categoryName = TypeProductCategories.toEnum(category).getDescription();
	}

	public ProductTopDTO(ItemProduct obj) {
		super();
		this.id = obj.getProduct().getId();
		this.description = obj.getProduct().getDescription();
		this.setRate(obj.getRate());
		this.price = obj.getProduct().getPrice();
		this.fromCountry = obj.getProduct().getFromCountry();
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

	public String getFromCountry() {
		return fromCountry;
	}

	public void setFromCountry(String fromCountry) {
		this.fromCountry = fromCountry;
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

}
