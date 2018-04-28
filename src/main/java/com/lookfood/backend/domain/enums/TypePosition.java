package com.lookfood.backend.domain.enums;

public enum TypePosition {

	CHEF(1, "Chef"),
	ASSISTANT(2, "Assitente"),
	MANAGER(3,"Gerente"),
	WAITER(4,"Garçon");
	
	
	private Integer cod;
	private String description;
;	
	private TypePosition(Integer cod, String description) {
		this.cod = cod;
		this.description = description;
	}
	
	public Integer getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}
	
	public static TypePosition toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (TypePosition x : TypePosition.values() ) {
			if (cod.equals(x.getCod())) {
				return x ;
			}				
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
