package com.lookfood.backend.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lookfood.backend.domain.enums.TypeStatus;

@Entity
public class Catalog implements Serializable{
	
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

	public Catalog() {
		super();
	}

	public Catalog(Integer id, TypeStatus status, Date date, Partner partner) {
		super();
		this.id = id;
		this.status = status.getCod();
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
		Catalog other = (Catalog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	
}