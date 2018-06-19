package com.lookfood.backend.domain.enums;

public enum TypeProductCategories {

	PASTA(1, "Massas"),
	MEAT(2, "Carnes"),
	SEAFOOD(3, "Frutos do Mar"),
	DESSERT(4, "Sobremesas"),
	SALAD(5, "Saladas"),
	PRIMARY(6, "Entradas"),
	GRAIN(7, "Grãos"),
	MAIN(8, "Pratos Principais"),
	COFFEE(9, "Cafés"),
	VEGETARIAN(10, "Vegetarianos"),
	VEGAN(11, "Veganos"),
	APPETIZER(12, "Petiscos"),
	SOUP(13, "Sopas");
	
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
