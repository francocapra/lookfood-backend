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
public class Review implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //DEfinindo a estrategia de geração automatica dos ID, dependendo do banco tem q ser <> IDENTITY
	private Integer id;
	private Integer status;
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date date;
	
	
	public Review() {
		super();
	}

	public Review(Integer id, TypeStatus status, Date date ) {
		super();
		this.id = id;
		this.status = status.getCod();
		this.date = date;
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
	
	
	
}
