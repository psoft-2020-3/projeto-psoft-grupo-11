package com.ufcg.psoft.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class VendaProduto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;

	private int quantidade;
	
	@JsonIgnore
	@ElementCollection
	private List<Long> lotes;
	
	@JsonIgnore
	@ElementCollection
	private List<Integer> qtdPorLote;
	
	public VendaProduto() {
		
	}

	public VendaProduto(Produto produto, int quantidade, List<Long> lotes, List<Integer> qtdPorLote) {
		super();
		this.produto = produto;
		this.quantidade = quantidade;
		this.lotes = lotes;
		this.qtdPorLote = qtdPorLote;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Long getId() {
		return id;
	}

	public List<Long> getLotes() {
		return lotes;
	}

	public void setLotes(List<Long> lotes) {
		this.lotes = lotes;
	}

	public List<Integer> getQtdPorLote() {
		return qtdPorLote;
	}

	public void setQtdPorLote(List<Integer> qtdPorLote) {
		this.qtdPorLote = qtdPorLote;
	}
	
	
}
