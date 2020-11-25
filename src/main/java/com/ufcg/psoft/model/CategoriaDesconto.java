package com.ufcg.psoft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


// DESCONTO DA CATEGORIA DO PRODUTO
@Entity
public class CategoriaDesconto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String categoria;
	
	@Enumerated(EnumType.STRING)
	private TipoDesconto desconto;
	
	public CategoriaDesconto() {
		
	}

	public CategoriaDesconto(String categoria, TipoDesconto desconto) {
		super();
		this.categoria = categoria;
		this.desconto = desconto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public TipoDesconto getDesconto() {
		return desconto;
	}

	public void setDesconto(TipoDesconto desconto) {
		this.desconto = desconto;
	}

	public Long getId() {
		return id;
	}
}
