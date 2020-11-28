package com.ufcg.psoft.service;

import java.util.Iterator;
import java.util.List;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.DTO.LoteInputDTO;

import exceptions.ObjetoInexistenteException;

public interface LoteService {

	List<Lote> findAll();
	
	List<Lote> findAllLotesByProduto(Long idProduto) throws ObjetoInexistenteException;
	
	List<Lote> findAllLotesByProdutoDentroDaValidade(Produto produto) throws ObjetoInexistenteException;

	Lote findById(long id) throws ObjetoInexistenteException;

	void updateProduto(Lote user) throws ObjetoInexistenteException;

	void deleteLoteById(long id) throws ObjetoInexistenteException;

	int size();

	Iterator<Lote> getIterator();

	Lote save(LoteInputDTO lote);
}
