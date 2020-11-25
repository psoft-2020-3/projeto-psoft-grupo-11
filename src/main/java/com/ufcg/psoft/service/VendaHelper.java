package com.ufcg.psoft.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.VendaProduto;
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
	public List<VendaProduto> criarVendaProduto(Map<Long, Integer> dadosVenda) {
		List<VendaProduto> retorno = new ArrayList<>();
		VendaProduto vendaProduto = new VendaProduto();
		Produto produto = new Produto();

		for (Map.Entry<Long, Integer> pair : dadosVenda.entrySet()) {
			produto = this.produtoRepository.findById(pair.getKey()).get();
			vendaProduto.setProduto(produto);
			vendaProduto.setQuantidade(pair.getValue());
			retorno.add(vendaProduto);
			vendaProduto = new VendaProduto();
		}

		return retorno;
	}

	// DECREMENTA PRODUTOS DOS LOTES
	public void retiraProdutos(List<Produto> produtosDaVenda, Map<Long, Integer> dadosDaVenda) throws ObjetoInvalidoException {
		List<Lote> lotesDoProduto;
		int qtdCount;
		
		for (Produto produto : produtosDaVenda) {
			int index = 0;
			qtdCount = dadosDaVenda.get(produto.getId());
			lotesDoProduto = this.loteRepository.findAllByProduto(produto);
			
			while (qtdCount > 0) {
				Lote lote = lotesDoProduto.get(index);
				if (lote.getNumeroDeItens() > qtdCount) {
					lote.setNumeroDeItens(lote.getNumeroDeItens() - qtdCount);
					break;
				} else {
					qtdCount -= lote.getNumeroDeItens();
					lote.setNumeroDeItens(0);
				}
				this.loteRepository.save(lote);
				index++;
			}
			
			if (this.loteRepository.countItensByProduct(produto.getId()) == 0) {
				produto.mudaSituacao(2);
				this.produtoRepository.save(produto);
			}
		}
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
