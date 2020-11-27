package com.ufcg.psoft.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.Venda;
import com.ufcg.psoft.model.VendaProduto;
import com.ufcg.psoft.model.DTO.LoteVendaDTO;
import com.ufcg.psoft.model.DTO.VendaInputDTO;
import com.ufcg.psoft.repositories.LoteRepository;
import com.ufcg.psoft.repositories.ProdutoRepository;
import com.ufcg.psoft.repositories.VendaProdutoRepository;
import com.ufcg.psoft.repositories.VendaRepository;

import exceptions.ObjetoInvalidoException;

@Service
public class VendaServiceImpl implements VendaService {

	@Autowired
	VendaRepository vendaRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	LoteRepository loteRepository;

	@Autowired
	VendaProdutoRepository vendaProdutoRepository;

	@Autowired
	VendaHelper helper;

	// CRIA UMA VENDA
	@Override
	public Venda save(VendaInputDTO venda) throws Exception {
		Map<Long, Integer> dadosDaVenda = venda.getDadosDaVenda();

		// VERIFICA SE TODOS OS PORDUTOS ESTAO DISPONIVEIS
		if (this.helper.verificaDisponibilidade(dadosDaVenda.keySet()) == false) {
			throw new Exception("Algum produto não disponível");
		}

		// VERIFICO SE TODOS OS PRODUTOS TEM QTD SUFICIENTE
		if (this.helper.verificaQuantidades(dadosDaVenda) == false) {
			throw new Exception("Alguma quantidade não disponível");
		}

		// RECUPERA PRODUTOS DA VENDA
		List<Produto> produtosDaVenda = this.produtoRepository.findAllById(dadosDaVenda.keySet());

		// DECREMENTA PRODUTO DOS LOTES
		Map<Long, LoteVendaDTO> lotesUsados = this.helper.retiraProdutos(produtosDaVenda, dadosDaVenda);

		BigDecimal total = this.helper.calcularPreco(dadosDaVenda);

		List<VendaProduto> itensVenda = this.helper.criarVendaProduto(venda.getDadosDaVenda(), lotesUsados);

		Venda vendaFinal = new Venda(itensVenda, total);
		Venda retorno = this.vendaRepository.save(vendaFinal);

		return retorno;
	}

	@Override
	public List<Venda> findAll() {
		List<Venda> vendas = this.vendaRepository.findAll();
		return vendas;
	}

	// DELETAR VENDA
	@Override
	public void delete(Long idVenda) throws ObjetoInvalidoException {
		List<VendaProduto> produtosVenda = this.vendaProdutoRepository.findAllByIdVenda(idVenda);
		Lote lote;
		VendaProduto vendaProduto;

		for (int i = 0; i < produtosVenda.size(); i++) {
			vendaProduto = produtosVenda.get(i);
			for (int j = 0; j < vendaProduto.getLotes().size(); j++) {
				lote = this.loteRepository.findById(vendaProduto.getLotes().get(j)).get();
				lote.setNumeroDeItens(lote.getNumeroDeItens() + vendaProduto.getQtdPorLote().get(j));
				this.loteRepository.save(lote);

			}

			Produto produto = this.produtoRepository.findById(vendaProduto.getProduto().getId()).get();
			produto.setDisponivel(true);
			this.produtoRepository.save(produto);
		}

		this.vendaRepository.deleteById(idVenda);
	}

	// ORDENAR PRODUTOS POR INFORMACOES IMPORTANTES
	@Override
	public List<Venda> findAllOrdered(String field) {
		List<Venda> vendas = this.vendaRepository.findAll(Sort.by(Sort.Order.asc(field).ignoreCase()));
		return vendas;
	}

}
