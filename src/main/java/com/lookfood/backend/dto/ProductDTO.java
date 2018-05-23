package com.lookfood.backend.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.lookfood.backend.domain.Product;

public class ProductDTO  implements Serializable{	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5,max=80,message="O tamanho deve ser entre 5 a 80 caracteres")
	private String description;
	
	private Date date;
	
	public ProductDTO() {
		super();
	}
		
	public ProductDTO(Product obj) {
		super();
		this.id = obj.getId();
		this.description = obj.getDescription();
		this.date = obj.getCreatedDate();
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
