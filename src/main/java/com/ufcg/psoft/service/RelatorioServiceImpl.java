package com.ufcg.psoft.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.Venda;
import com.ufcg.psoft.model.DTO.LotesQuantidadesDTO;
import com.ufcg.psoft.model.DTO.RelatorioDTO;
import com.ufcg.psoft.repositories.LoteRepository;
import com.ufcg.psoft.repositories.ProdutoRepository;
import com.ufcg.psoft.repositories.VendaRepository;

//RELATORIO GERAL
@Service
public class RelatorioServiceImpl implements RelatorioService{

	@Autowired
	VendaRepository vendaRepository;
	
	@Autowired
	LoteRepository loteRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Override
	public RelatorioDTO gerarRelatorio() {
		RelatorioDTO relatorio = new RelatorioDTO();
		List<LotesQuantidadesDTO> lista = new ArrayList<LotesQuantidadesDTO>();
		LotesQuantidadesDTO lotesQuantidadesDTO;
		
		Double receitaTotal = this.vendaRepository.calculateProfit();
		List<Venda> vendas = this.vendaRepository.findAll();
		List<Produto> produtos = this.produtoRepository.findAll();
		
		int qtdItens;
		int qtdLotes;
		List<String> validades;
		for (Produto produto : produtos) {
			validades = new ArrayList<String>();
			qtdItens = this.loteRepository.countItensByProduct(produto.getId());
			qtdLotes = this.loteRepository.countlotesByProduct(produto.getId());
			validades = this.loteRepository.findValidadesPorProduto(produto.getId());
			
			lotesQuantidadesDTO = new LotesQuantidadesDTO(produto, qtdLotes, qtdItens, validades);
			lista.add(lotesQuantidadesDTO);
		}
		
		relatorio.setReceitaTotal(receitaTotal);
		relatorio.setVendas(vendas);
		relatorio.setProdutos(lista);
		
		return relatorio;
	}

}
