package com.ufcg.psoft.model.DTO;

import java.util.List;

import com.ufcg.psoft.model.Produto;

public class LotesQuantidadesDTO {

	private Produto produto;
	private Integer qtdLotes;
	private Integer qtdProdutos;
	private List<String> validades;

	public LotesQuantidadesDTO(Produto produto, Integer qtdLotes, Integer qtdProdutos, List<String> validades) {
		super();
		this.setProduto(produto);
		this.qtdLotes = qtdLotes;
		this.qtdProdutos = qtdProdutos;
		this.validades = validades;
	}

	public Integer getQtdLotes() {
		return qtdLotes;
	}

	public void setQtdLotes(Integer qtdLotes) {
		this.qtdLotes = qtdLotes;
	}

	public Integer getQtdProdutos() {
		return qtdProdutos;
	}

	public void setQtdProdutos(Integer qtdProdutos) {
		this.qtdProdutos = qtdProdutos;
	}

	public List<String> getValidades() {
		return validades;
	}

	public void setValidades(List<String> validades) {
		this.validades = validades;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
}
