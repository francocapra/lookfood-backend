package com.lookfood.backend.domain.enums;

public enum TypeStatus {
	
	OPEN(1, "Aberto"),
	CLOSE(2, "Fechado");
	
	private Integer cod;
	private String description;
	
	private TypeStatus(Integer cod, String description) {
		this.cod = cod;
		this.description = description;
	}
	
	public Integer getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}
	
	public static TypeStatus toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (TypeStatus x : TypeStatus.values() ) {
			if (cod.equals(x.getCod())) {
				return x ;
			}				
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
