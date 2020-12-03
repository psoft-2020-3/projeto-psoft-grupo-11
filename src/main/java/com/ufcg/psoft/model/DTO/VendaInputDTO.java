package com.ufcg.psoft.model.DTO;

import java.util.HashMap;
import java.util.Map;

// CRIAR VENDA
public class VendaInputDTO {
	private Map<Long, Integer> dadosDaVenda = new HashMap<>();

	public Map<Long, Integer> getDadosDaVenda() {
		return dadosDaVenda;
	}

	public void setDadosDaVenda(Map<Long, Integer> dadosDaVenda) {
		this.dadosDaVenda = dadosDaVenda;
	}
}
