package com.ufcg.psoft.model;

public enum TipoDesconto {
	SEM_DESCONTO("Sem desconto", 0), BOM_DESCONTO("Bom desconto", 10), OTIMO_DESCONTO("Ótimo desconto",
			25), SUPER_DESCONTO("Super Desconto", 50);

	private final String key;
	private final Integer value;

	TipoDesconto(String key, Integer value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public Integer getValue() {
		return value;
	}
}
