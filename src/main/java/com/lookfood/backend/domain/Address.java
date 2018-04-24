package com.lookfood.backend.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Address implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String street;
	private String complement;
	private String district;
	private String postcode;
	

	@ManyToOne
	@JoinColumn(name="partner_id")
	@JsonIgnore
	private Partner partner;
		
	@ManyToOne
	@JoinColumn(name="city_id")
	private City city;
	
	
	public Address() {
		super();
	}


	public Address(Integer id, String street, String complement, String district, String postcode, Partner partner, City city) {
		super();
		this.id = id;
		this.street = street;
		this.complement = complement;
		this.district = district;
		this.postcode = postcode;
		this.partner = partner;
		this.city= city;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getComplement() {
		return complement;
	}


	public void setComplement(String complement) {
		this.complement = complement;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getPostcode() {
		return postcode;
	}


	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}


	public Partner getPartner() {
		return partner;
	}


	public void setCliente(Partner partner) {
		this.partner = partner;
	}


	public City getCity() {
		return city;
	}


	public void setCidade(City city) {
		this.city = city;
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
		Address other = (Address) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
	
}
