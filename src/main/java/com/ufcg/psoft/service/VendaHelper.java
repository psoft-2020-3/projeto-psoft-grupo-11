package com.ufcg.psoft.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.VendaProduto;
import com.ufcg.psoft.model.DTO.LoteVendaDTO;
import com.ufcg.psoft.repositories.LoteRepository;
import com.ufcg.psoft.repositories.ProdutoRepository;

import exceptions.ObjetoInvalidoException;

@Service
public class VendaHelper {
	
	@Autowired
	LoteRepository loteRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	public Boolean verificaQuantidades(Map<Long, Integer> dadosVenda) {
		Boolean retorno = false;
		int quantidade = 0;

		for (Map.Entry<Long, Integer> pair : dadosVenda.entrySet()) {
			quantidade = this.loteRepository.countItensByProduct(pair.getKey());
			if (quantidade < pair.getValue()) {
				retorno = false;
				break;
			} else {
				retorno = true;
			}
		}
		
		return retorno;
	}
	
	public Boolean verificaDisponibilidade(Set<Long> set) {
		Boolean retorno = false;
		List<Long> ids = new ArrayList<Long>(set);
		List<Produto> produtos = this.produtoRepository.findProductsCompra(ids);
		if (produtos.size() == set.size()) {
			retorno = true;
		}

		return retorno;
	}
	
	// CRIAR VENDA DO PRODUTO
	public List<VendaProduto> criarVendaProduto(Map<Long, Integer> dadosVenda, Map<Long, LoteVendaDTO> lotes) {
		List<VendaProduto> retorno = new ArrayList<>();
		VendaProduto vendaProduto = new VendaProduto();
		Produto produto = new Produto();

		for (Map.Entry<Long, Integer> pair : dadosVenda.entrySet()) {
			produto = this.produtoRepository.findById(pair.getKey()).get();
			
			vendaProduto.setProduto(produto);
			vendaProduto.setQuantidade(pair.getValue());
			vendaProduto.setLotes(lotes.get(pair.getKey()).getIdsLotes());
			vendaProduto.setQtdPorLote(lotes.get(pair.getKey()).getQtdPorLote());
			
			retorno.add(vendaProduto);
			vendaProduto = new VendaProduto();
		}

		return retorno;
	}

	// DECREMENTA PRODUTOS DOS LOTES
	public Map<Long, LoteVendaDTO> retiraProdutos(List<Produto> produtosDaVenda, Map<Long, Integer> dadosDaVenda) throws ObjetoInvalidoException {
		List<Lote> lotesDoProduto;
		List<Long> idsLotes;
		List<Integer> qtdPorLote;
		Map<Long, LoteVendaDTO> retorno = new HashMap<Long, LoteVendaDTO>();
		LoteVendaDTO loteVendaDTO;
		int qtdCount;
		
		for (Produto produto : produtosDaVenda) {
			int index = 0;
			qtdPorLote = new ArrayList<Integer>();
			idsLotes = new ArrayList<Long>();
			qtdCount = dadosDaVenda.get(produto.getId());
			lotesDoProduto = this.loteRepository.findAllByProduto(produto);
			
			while (qtdCount > 0) {
				Lote lote = lotesDoProduto.get(index);
				idsLotes.add(lote.getId());
				
				if (lote.getNumeroDeItens() > qtdCount) {
					lote.setNumeroDeItens(lote.getNumeroDeItens() - qtdCount);
					qtdPorLote.add(qtdCount);
					break;
				} else {
					qtdCount -= lote.getNumeroDeItens();
					qtdPorLote.add(lote.getNumeroDeItens());
					lote.setNumeroDeItens(0);
				}
				
				this.loteRepository.save(lote);
				index++;
			}
			
			loteVendaDTO = new LoteVendaDTO(idsLotes, qtdPorLote);
			retorno.put(produto.getId(), loteVendaDTO);
			
			if (this.loteRepository.countItensByProduct(produto.getId()) == 0) {
				produto.setDisponivel(false);
				this.produtoRepository.save(produto);
			}
		}
		return retorno;
	}

	// RETORNA PREÇO TOTAL
	public BigDecimal calcularPreco(Map<Long, Integer> dadosDaVenda) {
		BigDecimal valorTotal = BigDecimal.ZERO;
		BigDecimal totalProduto = BigDecimal.ZERO;
		BigDecimal desconto = BigDecimal.ZERO;
		Produto produto;
		
		for (Map.Entry<Long, Integer> pair : dadosDaVenda.entrySet()) {
			produto = this.produtoRepository.findById(pair.getKey()).get();
			desconto = new BigDecimal(produto.getCategoria().getDesconto().getValue());
			desconto = desconto.divide(new BigDecimal(100));
			desconto = (BigDecimal.ONE).subtract(desconto);
			
			if (desconto == BigDecimal.ZERO) {
				desconto = desconto.add(new BigDecimal(1));
			}
			
			totalProduto = produto.getPreco().multiply(new BigDecimal(pair.getValue())); 
			valorTotal = valorTotal.add(totalProduto.multiply(desconto)); 
		}
		
		return valorTotal;
	}
}
