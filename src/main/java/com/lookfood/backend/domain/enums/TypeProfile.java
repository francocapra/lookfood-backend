package com.lookfood.backend.domain.enums;

public enum TypeProfile {

	ADMIN(1, "ROLE_ADMIN"),
	PARTNER(2,"ROLE_PARTNER");
	
	
	private Integer cod;
	private String description;
;	
	private TypeProfile(Integer cod, String description) {
		this.cod = cod;
		this.description = description;
	}
	
	public Integer getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}
	
	public static TypeProfile toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (TypeProfile x : TypeProfile.values() ) {
			if (cod.equals(x.getCod())) {
				return x ;
			}				
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
