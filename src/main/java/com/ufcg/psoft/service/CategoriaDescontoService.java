package com.ufcg.psoft.service;

import com.ufcg.psoft.model.CategoriaDesconto;


public interface CategoriaDescontoService {

	CategoriaDesconto save(CategoriaDesconto categoriaDesconto);
	
	CategoriaDesconto update(String categoria, String desconto) throws Exception;
}
