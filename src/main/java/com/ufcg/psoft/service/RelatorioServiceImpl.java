package com.ufcg.psoft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Venda;
import com.ufcg.psoft.model.DTO.RelatorioDTO;
import com.ufcg.psoft.repositories.VendaRepository;



//RELATORIO GERAL
@Service
public class RelatorioServiceImpl implements RelatorioService{

	@Autowired
	VendaRepository vendaRepository;
	
	@Override
	public RelatorioDTO gerarRelatorio() {
		RelatorioDTO relatorio = new RelatorioDTO();
		
		Double receitaTotal = this.vendaRepository.calculateProfit();
		List<Venda> vendas = this.vendaRepository.findAll();
		
		relatorio.setReceitaTotal(receitaTotal);
		relatorio.setVendas(vendas);
		
		return relatorio;
	}

}
