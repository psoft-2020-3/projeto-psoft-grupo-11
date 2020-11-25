package com.ufcg.psoft.service;

import java.util.List;

import com.ufcg.psoft.model.Venda;
import com.ufcg.psoft.model.DTO.VendaInputDTO;

import exceptions.ObjetoInvalidoException;


public interface VendaService {

	List<Venda> findAll();
	
	Venda save(VendaInputDTO venda) throws Exception;
	
	void delete(Long idVenda) throws ObjetoInvalidoException;

	List<Venda> findAllOrdered(String field);
}
