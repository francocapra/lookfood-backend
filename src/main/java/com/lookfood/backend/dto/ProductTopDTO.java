package com.lookfood.backend.dto;

import java.io.Serializable;

import com.lookfood.backend.domain.ItemProduct;
import com.lookfood.backend.domain.enums.TypeCountries;

public class ProductTopDTO  implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String description;
	
	private Integer rate;
	
	private double price;
	
	private Integer fromCountry;
	
	private String fromCountryName;
	
	public String getFromCountryName() {
		return fromCountryName;
	}

	public void setFromCountryName(String fromCountryName) {
		this.fromCountryName = fromCountryName;
	}

	public void setNumberOfReviews(long numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}

	private long numberOfReviews;
	
	public ProductTopDTO() {
		super();
	}
		
	public ProductTopDTO(Integer id, String description, Integer rate, double price, Integer fromCountry,
			long numberOfReviews) {
		super();
		this.id = id;
		this.description = description;
		this.rate = rate;
		this.price = price;
		this.fromCountry = fromCountry;
		this.numberOfReviews = numberOfReviews;
		this.fromCountryName = TypeCountries.toEnum(fromCountry).getDescription();
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

	public Integer getFromCountry() {
		return fromCountry;
	}

	public void setFromCountry(Integer fromCountry) {
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

	public void setNumberOfReviews(Integer numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}

}
