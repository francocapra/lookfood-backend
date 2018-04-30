package com.lookfood.backend.dto;

import java.io.Serializable;
import java.util.Date;

import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.domain.Review;

public class ReviewDTO implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String status;
	private Date date;
	private Partner partner;
	
	public ReviewDTO() {
		super();
	}	
	public ReviewDTO(Review obj) {
		super();
		this.id = obj.getId();
		this.status = obj.getStatus().getDescription();
		this.date = obj.getDate();
		this.partner = obj.getPartner();
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}	
}