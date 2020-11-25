package com.ufcg.psoft.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.DTO.PrecoSituacaoDTO;
import com.ufcg.psoft.model.DTO.ProdutoInputDTO;
import com.ufcg.psoft.repositories.CategoriaDescontoRepository;
import com.ufcg.psoft.repositories.ProdutoRepository;

import exceptions.ObjetoInvalidoException;

import com.ufcg.psoft.model.CategoriaDesconto;
import com.ufcg.psoft.model.Produto;


@Service
public class ProdutoServiceImpl implements ProdutoService {


	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaDescontoRepository categoriaDescontoRepository;

	@Override
	public List<Produto> findAll() {
		List<Produto> produtos = this.produtoRepository.findAll();

		return produtos;
	}

	@Override
	public Produto save(ProdutoInputDTO produtoDTO) throws ObjetoInvalidoException {
		CategoriaDesconto categoria = this.categoriaDescontoRepository.findById(produtoDTO.getId_categoria()).get();
		Produto produto = new Produto(produtoDTO.getNome(), produtoDTO.getPreco(), produtoDTO.getCodigoBarra(), produtoDTO.getFabricante(), categoria, produtoDTO.getSituacao());
		produto = this.produtoRepository.save(produto);

		return produto;
	}

	@Override
	public Produto findById(long id) {
		Optional<Produto> produto = this.produtoRepository.findById(id);

		if (produto.get() != null) {
			return produto.get();
		} else {
			return null;
		}
		
	}

	// ● Eu, como cliente, gostaria de consultar a disponibilidade e o preço de cada
	// produto do supermercado (não precisa estar logado)
	@Override
	public List<PrecoSituacaoDTO> findPriceAndSituation() {
		List<PrecoSituacaoDTO> disponiveis = this.produtoRepository.findPriceAndSituationAvailable();
		List<PrecoSituacaoDTO> indisponiveis = this.produtoRepository.findPriceAndSituationUnavailable();

		disponiveis.addAll(indisponiveis);
		return disponiveis;
	}

	// ORDENAR PRODUTOS POR INFORMACOES IMPORTANTES
	@Override
	public List<Produto> findAllOrdered(String field) {
		List<Produto> produtos = produtoRepository.findAll(Sort.by(Sort.Order.asc(field).ignoreCase()));
		return produtos;
	}

	// PRIDUTOS INDISPONIVEIS
	public List<Produto> findAllUnavailable() {
		List<Produto> produtos = produtoRepository.findAllUnavailable();
		return produtos;
	}

}