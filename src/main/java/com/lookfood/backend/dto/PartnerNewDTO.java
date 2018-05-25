package com.lookfood.backend.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.br.CNPJ;

import com.lookfood.backend.services.validation.PartnerInsert;

@PartnerInsert
public class PartnerNewDTO implements Serializable{	
	private static final long serialVersionUID = 1L;

//	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5,max=120,message="O tamanho deve ser entre 5 a 120 caracteres")
	private String name;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email inválido")	
	private String email;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String password;
		
	
	
//	@NotEmpty(message="Preenchimento obrigatório")
	@CNPJ
	private String cnpj;

//	@NotEmpty(message="Preenchimento obrigatório")
	@URL	
	private String website;
	
//	@NotEmpty(message="Preenchimento obrigatório")
	private String street;
//	@NotEmpty(message="Preenchimento obrigatório")
	private String number;
	
	private String complement;
	
	private String district;
	
//	@NotEmpty(message="Preenchimento obrigatório")
	private String postcode;
	
//	@NotEmpty(message="Preenchimento obrigatório")
	private String phone1;
	private String phone2;
	private String phone3;
	
//	@NotEmpty(message="Preenchimento obrigatório")
	private Integer cityId;
		
	public PartnerNewDTO() {		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	
}
