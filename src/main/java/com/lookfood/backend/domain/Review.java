package com.lookfood.backend.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lookfood.backend.domain.enums.TypeStatus;

@Entity
@JsonPropertyOrder({ "id", "partnerId"})
public class Review implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer id;
	
	private Integer status;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="partner_id")
	private Partner partner;

	@OneToMany(mappedBy="id.review")
	private Set<ItemProduct> itemsProduct = new HashSet<>(); 

	@OneToMany(mappedBy="id.review")
	private Set<ItemProfessional> itemsProfessional = new HashSet<>(); 
	
	public Review() {
		super();
	}

	public Review(Integer id, TypeStatus status, Date date, Partner partner) {
		super();
		this.id = id;
		this.status = (status == null) ? null : status.getCod();
		this.date = date;
		this.partner = partner;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TypeStatus getStatus() {
		return TypeStatus.toEnum(status);
	}

	public void setStatus(TypeStatus status) {
		this.status = status.getCod();
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
	
	public Set<ItemProduct> getItemsProduct() {
		return itemsProduct;
	}

	public void setItemsProduct(Set<ItemProduct> productItems) {
		this.itemsProduct = productItems;
	}

	public Set<ItemProfessional> getItemsProfessional() {
		return itemsProfessional;
	}

	public void setItensProfessional(Set<ItemProfessional> professionalItems) {
		this.itemsProfessional = professionalItems;
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
		Review other = (Review) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		StringBuilder builder = new StringBuilder();
		builder.append("Avaliação: ");
		builder.append(getId());
		builder.append(", Data Avaliação: ");
		builder.append(sdf.format(getDate()));
		builder.append(", Parceiro: ");
		builder.append(getPartner().getName());
		builder.append(", Status da Avaliação: ");
		builder.append(getStatus().getDescription());
	
		builder.append("\nProdutos:\n");
		for (ItemProduct ip: getItemsProduct()) {			
			builder.append(ip.toString());
		};
	
		if ( getItemsProfessional() != null) {
			builder.append("\nServiços:\n");
			for (ItemProfessional ip: getItemsProfessional()) {			
				builder.append(ip.toString());
			};		
		};
		
		return builder.toString();
	}	
}
