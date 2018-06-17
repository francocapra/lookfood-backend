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
	
	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static TypeCountries toEnum(Integer code) {
		if (code == null) {
			return null;
		}
		
		for (TypeCountries x : TypeCountries.values() ) {
			if (code.equals(x.getCode())) {
				return x ;
			}				
		}
		
		throw new IllegalArgumentException("Id inválido: " + code);
	}
}
