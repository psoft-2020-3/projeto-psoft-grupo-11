package com.ufcg.psoft.model.DTO;

import java.util.List;

import com.ufcg.psoft.model.Venda;

//RELATORIO GERAL
public class RelatorioDTO {

	private Double receitaTotal;
	
	private List<Venda> vendas;
	
	private List<LotesQuantidadesDTO> produtos;
	
	public RelatorioDTO() {
		
	}

	public RelatorioDTO(Double receitaTotal, List<Venda> vendas) {
		super();
		this.receitaTotal = receitaTotal;
		this.vendas = vendas;
	}

	public Double getReceitaTotal() {
		return receitaTotal;
	}

	public void setReceitaTotal(Double receitaTotal) {
		this.receitaTotal = receitaTotal;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public List<LotesQuantidadesDTO> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<LotesQuantidadesDTO> produtos) {
		this.produtos = produtos;
	}
}
