package com.lookfood.backend.domain.enums;

public enum TypeCountries {
	
	BRAZIL(076, "Brazil"),
	USA(840, "United States"),
	MEXICO(484, "Mexico");
	
	private Integer code;
	private String description;
	
	private TypeCountries(Integer code, String description) {
		this.code = code;
		this.description = description;
	}
	
}
