package com.ufcg.psoft.service;

import com.ufcg.psoft.model.Categoria;

public interface CategoriaDescontoService {

	Categoria save(Categoria categoriaDesconto);
	
	Categoria update(String categoria, String desconto) throws Exception;
}
