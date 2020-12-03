package com.ufcg.psoft.model.DTO;

import java.util.List;

public class LoteVendaDTO {

	List<Long> idsLotes;
	List<Integer> qtdPorLote;
	
	public LoteVendaDTO(List<Long> idsLotes, List<Integer> qtdPorLote) {
		super();
		this.idsLotes = idsLotes;
		this.qtdPorLote = qtdPorLote;
	}

	public List<Long> getIdsLotes() {
		return idsLotes;
	}

	public void setIdsLotes(List<Long> idsLotes) {
		this.idsLotes = idsLotes;
	}

	public List<Integer> getQtdPorLote() {
		return qtdPorLote;
	}

	public void setQtdPorLote(List<Integer> qtdPorLote) {
		this.qtdPorLote = qtdPorLote;
	}
}
