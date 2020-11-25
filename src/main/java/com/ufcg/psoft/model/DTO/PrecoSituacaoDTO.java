package com.ufcg.psoft.model.DTO;

//● Eu, como cliente, gostaria de consultar a disponibilidade e o preço de cada
//produto do supermercado (não precisa estar logado)
public interface PrecoSituacaoDTO {

	String getNome();

	String getPreco();

	String getSituacao();
}