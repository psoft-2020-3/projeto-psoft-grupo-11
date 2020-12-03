package com.ufcg.psoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Categoria;
import com.ufcg.psoft.model.TipoDesconto;
import com.ufcg.psoft.repositories.CategoriaDescontoRepository;

@Service
public class CategoriaDescontoServiceImpl implements CategoriaDescontoService {

	@Autowired
	CategoriaDescontoRepository categoriaDescontoRepository;

	@Override
	public Categoria save(Categoria categoriaDesconto) {
		Categoria categoriaDescontoSalva = this.categoriaDescontoRepository.save(categoriaDesconto);
		return categoriaDescontoSalva;
	}

	@Override
	public Categoria update(String categoria, String desconto) throws Exception {
		Categoria categoriaDesconto = this.categoriaDescontoRepository.findByCategoria(categoria.toUpperCase());
		if (categoriaDesconto == null) {
			throw new Exception("Categoria não existe");
		}
		this.categoriaDescontoRepository.update(categoriaDesconto.getCategoria(),
				TipoDesconto.valueOf(desconto.toUpperCase()).toString());
		return categoriaDesconto;
	}

}
