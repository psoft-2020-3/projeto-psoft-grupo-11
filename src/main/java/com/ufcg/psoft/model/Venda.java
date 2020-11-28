package com.ufcg.psoft.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_venda", referencedColumnName="id")
	private List<VendaProduto> produtos;

	private BigDecimal valorTotal;

	public Venda() {
		this.produtos = new ArrayList<VendaProduto>();
	}

	public Venda(List<VendaProduto> produtos, BigDecimal valorTotal) {
		this.produtos = new ArrayList<VendaProduto>();
		this.produtos = produtos;
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Long getId() {
		return id;
	}

	public List<VendaProduto> getProdutos() {
		return produtos;
	}

	public void setProdutos(VendaProduto vendaProduto) {
		this.produtos.add(vendaProduto);
	}
}
