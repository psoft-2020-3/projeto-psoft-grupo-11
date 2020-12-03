package com.ufcg.psoft.service;

import java.util.List;

import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.DTO.PrecoSituacaoDTO;
import com.ufcg.psoft.model.DTO.ProdutoInputDTO;

import exceptions.ObjetoInvalidoException;

public interface ProdutoService {

	List<Produto> findAll();

	Produto findById(long id);

	List<Produto> findAllOrdered(String field);

	List<PrecoSituacaoDTO> findPriceAndSituation();

	Produto save(ProdutoInputDTO produtoDTO) throws ObjetoInvalidoException;

//	void updateProduto(Produto user);
//
//	void deleteProdutoById(long id);
//
//	boolean doesProdutoExist(Produto produto);
}
