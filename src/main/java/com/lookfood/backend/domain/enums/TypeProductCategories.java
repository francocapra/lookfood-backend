package com.lookfood.backend.domain.enums;

public enum TypeProductCategories {

	PASTA(1, "Massas"),
	MEAT(2, "Carnes"),
	SEAFOOD(3, "Frutos do Mar"),
	DESSERT(4, "Sobremesas");
	
	private Integer code;
	private String description;
	
	private TypeProductCategories(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static TypeProductCategories toEnum(Integer code) {
		if (code == null) {
			return null;
		}
		
		for (TypeProductCategories x : TypeProductCategories.values() ) {
			if (code.equals(x.getCode())) {
				return x ;
			}				
		}
		
		throw new IllegalArgumentException("Id inválido: " + code);
	}
	
}
