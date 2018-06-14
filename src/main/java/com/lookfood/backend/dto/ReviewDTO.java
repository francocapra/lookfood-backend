package com.lookfood.backend.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lookfood.backend.domain.Review;

public class ReviewDTO implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String status;
	private String reviewCode;
	
	private List<ItemReviewDTO> itemsReviewDTO = new ArrayList<>();
	
	public ReviewDTO() {
		super();
	}	
	
	public ReviewDTO(Review obj) {
		super();
		this.id = obj.getId();
		this.status = obj.getStatus().getDescription();
		this.reviewCode = obj.getReviewCode();
	}	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getReviewCode() {
		return reviewCode;
	}

	public void setReviewCode(String reviewCode) {
		this.reviewCode = reviewCode;
	}

	public List<ItemReviewDTO> getItemsReviewDTO() {
		return itemsReviewDTO;
	}

	public void setItemsReviewDTO(List<ItemReviewDTO> itemsReviewDTO) {
		this.itemsReviewDTO = itemsReviewDTO;
	}	
}
