package com.lookfood.backend.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.lookfood.backend.domain.ItemProduct;
import com.lookfood.backend.domain.ItemProfessional;
import com.lookfood.backend.domain.Partner;
import com.lookfood.backend.domain.Review;

public class ReviewDTO implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String status;
	private Date date;
	private Partner partner;
	private Set<ItemProduct> itensProduct = new HashSet<>(); 
	private Set<ItemProfessional> itensProfessional = new HashSet<>();
	
	public ReviewDTO() {
		super();
	}
	public ReviewDTO(Review obj) {
		super();
		this.id = obj.getId();
		this.status = obj.getStatus().getDescription();
		this.date = obj.getDate();
		this.partner = obj.getPartner();
		this.itensProduct = obj.getItensProduct();
		this.itensProfessional = obj.getItensProfessional();
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
	public Set<ItemProduct> getItensProduct() {
		return itensProduct;
	}
	public void setItensProduct(Set<ItemProduct> itensProduct) {
		this.itensProduct = itensProduct;
	}
	public Set<ItemProfessional> getItensProfessional() {
		return itensProfessional;
	}
	public void setItensProfessional(Set<ItemProfessional> itensProfessional) {
		this.itensProfessional = itensProfessional;
	} 
	
	
}
