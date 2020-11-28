package com.ufcg.psoft.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.DTO.LoteInputDTO;
import com.ufcg.psoft.repositories.LoteRepository;
import com.ufcg.psoft.repositories.ProdutoRepository;

import exceptions.ObjetoInexistenteException;

@Service("loteService")
public class LoteServiceImpl implements LoteService {

	@Autowired
	private LoteRepository loteRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public Lote save(LoteInputDTO loteDTO) {
		Produto produto = this.findProduct(loteDTO.getIdProduto());
		
		if (produto != null) {
			Lote lote = new Lote(produto, loteDTO.getNumeroDeItens(), loteDTO.getDataDeValidade());
			Lote loteSalvo = this.loteRepository.save(lote);
			produto.setDisponivel(true);
			this.produtoRepository.save(produto);
			return loteSalvo;
		} else {
			return null;
		}
	}
	@Override
	public Lote findById(long id) throws ObjetoInexistenteException {
		Optional<Lote> lotePesquisado = loteRepository.findById(id);
		if (!lotePesquisado.isPresent()) {
			throw new ObjetoInexistenteException("Lote não encontrado");
		}
		return lotePesquisado.get();
	}

	@Override
	public void updateProduto(Lote lote) throws ObjetoInexistenteException {
		Optional<Lote> lotePesquisado = loteRepository.findById(lote.getId());
		if (!lotePesquisado.isPresent()) {
			throw new ObjetoInexistenteException("Lote não encontrado");
		}
		lotePesquisado.get().setDataDeValidade(lote.getDataDeValidade());
		lotePesquisado.get().setNumeroDeItens(lote.getNumeroDeItens());
		lotePesquisado.get().setProduto(lote.getProduto());
		loteRepository.save(lotePesquisado.get());

	}

	@Override
	public void deleteLoteById(long id) throws ObjetoInexistenteException {
		Optional<Lote> lotePesquisado = loteRepository.findById(id);
		if (!lotePesquisado.isPresent()) {
			throw new ObjetoInexistenteException("Lote não encontrado");
		}
		loteRepository.delete(lotePesquisado.get());
	}


	public List<Lote> findAllLotesByProduto(Long idProduto) {
		Produto produto = this.findProduct(idProduto);
		
		List<Lote> lotes = this.loteRepository.findByProduto(produto);
		return lotes;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Lote> findAllLotesByProdutoDentroDaValidade(Produto produto) throws ObjetoInexistenteException{
		List<Lote> lotes = loteRepository.findAllByProduto(produto);
		List<Lote> retorno = new ArrayList<Lote>();
		if (lotes.isEmpty()) {
			throw new ObjetoInexistenteException("Lote de Produto não cadastrado");
		}
		for (Lote lote : lotes) {
			Date validade = new Date(lote.getDataDeValidade().replaceAll("/", "-"));
			String data = new Date().toGMTString(); 
		}
		return retorno;
	}

	@Override
	public List<Lote> findAll() {
		List<Lote> lotes = this.loteRepository.findAll();
		return lotes;
	}

	@Override
	public int size() {
		return findAll().size();
	}

	@Override
	public Iterator<Lote> getIterator() {
		return null;
	}
	
//	private boolean checkIfIsAvailable(Long idProduto) throws ObjetoInvalidoException {
//		Boolean isAvailable;
//		Integer qtd_itens = this.loteRepository.countItensByProduct(idProduto);
//		
//		if (qtd_itens == null || qtd_itens == 0) {
//			isAvailable = false;
//		} else {
//			isAvailable = true;
//		}
//		
//		return isAvailable;
//	}
	
	private Produto findProduct(Long idProduto) {
		Optional<Produto> produto = this.produtoRepository.findById(idProduto);
		if (produto.get() != null) {
			return produto.get();
		} else {
			return null;
		}
	}
}
