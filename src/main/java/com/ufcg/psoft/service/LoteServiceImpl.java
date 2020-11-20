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
import com.ufcg.psoft.repositories.LoteRepository;

import exceptions.ObjetoInexistenteException;

@Service("loteService")
public class LoteServiceImpl implements LoteService {

	@Autowired
	private LoteRepository loteRepository;

	@Override
	public Lote saveLote(Lote lote) {
		loteRepository.save(lote);
		return lote;
	}

	@Override
	public Lote findById(long id) throws ObjetoInexistenteException {
		Optional<Lote> lotePesquisado = loteRepository.findById(id);
		if (lotePesquisado.isEmpty()) {
			throw new ObjetoInexistenteException("Lote não encontrado");
		}
		return lotePesquisado.get();
	}

	@Override
	public void updateProduto(Lote lote) throws ObjetoInexistenteException {
		Optional<Lote> lotePesquisado = loteRepository.findById(lote.getId());
		if (lotePesquisado.isEmpty()) {
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
		if (lotePesquisado.isEmpty()) {
			throw new ObjetoInexistenteException("Lote não encontrado");
		}
		loteRepository.delete(lotePesquisado.get());
	}

	@Override
	public List<Lote> findAllLotesByProduto(Produto produto) throws ObjetoInexistenteException {
		List<Lote> lotes = loteRepository.findAllByProduto(produto);
		if (lotes.isEmpty()) {
			throw new ObjetoInexistenteException("Lote de Produto não cadastrado");
		}
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
	public List<Lote> findAllLotes() {
		return loteRepository.findAll();
	}

	@Override
	public int size() {
		return findAllLotes().size();
	}

	@Override
	public Iterator<Lote> getIterator() {
		return null;
	}
}
