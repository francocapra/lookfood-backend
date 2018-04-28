package com.lookfood.backend.dto;

import java.io.Serializable;

import com.lookfood.backend.domain.Professional;
import com.lookfood.backend.domain.enums.TypePosition;

public class ProfessionalDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String position;
	
	public ProfessionalDTO() {
		super();
	}
	
	
	public ProfessionalDTO(Professional obj) {
		super();
		this.id = obj.getId();
		this.name = obj.getName();
		this.position = obj.getPosition().getDescription();
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(TypePosition position) {
		this.position = position.getDescription();
	}
	
	
}
