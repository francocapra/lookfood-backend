package com.lookfood.backend.domain.enums;

public enum TypeStatus {
	
	ACTIVE(1, "Ativo"),
	INACTIVE(2, "Inativo");
	
	private Integer cod;
	private String descricao;
	
	private TypeStatus(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
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
