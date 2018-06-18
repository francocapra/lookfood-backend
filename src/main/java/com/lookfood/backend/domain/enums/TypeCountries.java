package com.lookfood.backend.domain.enums;

public enum TypeCountries {
	
	BRAZIL(1, "BR", "Brazil"),
	USA(2, "US", "United States"),
	MEXICO(3, "MX", "Mexico");
	
	private Integer code;
	private String isoCode;
	private String description;
	
	private TypeCountries(Integer code, String isoCode, String description) {
		this.code = code;
		this.description = description;
		this.isoCode = isoCode;
	}
	
	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public String getIsoCode() {
		return isoCode;
	}
	
	public static TypeCountries toEnum(String isoCode) {
		if (isoCode == null) {
			return null;
		}
		
		for (TypeCountries x : TypeCountries.values() ) {
			if (isoCode.equals(x.getIsoCode())) {
				return x ;
			}				
		}
		
		throw new IllegalArgumentException("Id inválido: " + isoCode);
	}
}
