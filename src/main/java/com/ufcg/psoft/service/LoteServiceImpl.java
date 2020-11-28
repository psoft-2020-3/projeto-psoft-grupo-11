package com.ufcg.psoft.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import exceptions.ObjetoInexistenteException;

@Service("loteService")
public class LoteServiceImpl implements LoteService {

	@Autowired
	private LoteRepository loteRepository;

	@Autowired
	private ProdutoServiceImpl produtoService;

	@Override
	public Lote save(LoteInputDTO loteDTO) throws Exception {
		Produto produto = this.findProduct(loteDTO.getIdProduto());

		if (produto != null) {
			Lote lote = new Lote(produto, loteDTO.getNumeroDeItens(), loteDTO.getDataDeValidade());
			Lote loteSalvo = this.loteRepository.save(lote);
			produto.setDisponivel(true);
			this.produtoService.save(produto);
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

	public List<Lote> findAllLotesByProduto(Long idProduto) throws ObjetoInexistenteException {
		Produto produto = this.findProduct(idProduto);

		List<Lote> lotes = this.loteRepository.findByProduto(produto);
		return lotes;
	}

	@Override
	public List<Lote> findAllLotesComPoucaValidade() throws Exception {
		List<Lote> lotes = loteRepository.findAll();
		List<Lote> retorno = new ArrayList<Lote>();
		if (lotes.isEmpty()) {
			throw new ObjetoInexistenteException("Lote de Produto não cadastrado");
		}
		DateFormat newStyle1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat newStyle2 = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String hoje = newStyle1.format(date);
		for (Lote lote : lotes) {
			Date dataValidade = newStyle2.parse((lote.getDataDeValidade()).replaceAll("/", "-"));
			long diferencaDias = (dataValidade.getTime() - newStyle1.parse(hoje).getTime()) / (1000 * 60 * 60 * 24);
			if (diferencaDias <= 31) {
				retorno.add(lote);
			}
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

	private Produto findProduct(Long idProduto) throws ObjetoInexistenteException {
		Produto produto = this.produtoService.findById(idProduto);
		if (produto == null) {
			throw new ObjetoInexistenteException("Produto não encontrado");
		}
		return produto;
	}

	public List<Lote> lotesAcabando() throws Exception {
		List<Lote> lotes = loteRepository.findAll();
		if (lotes.isEmpty()) {
			throw new Exception("Não existe nenhum lote com poucas unidades");
		}
		List<Lote> retorno = new ArrayList<Lote>();
		for (Lote lote : lotes) {
			if (lote.getNumeroDeItens() < 15)
				retorno.add(lote);
		}
		return retorno;
	}

	public List<Produto> verificaStatusProdutos() throws Exception {
		List<Produto> produtos = produtoService.findAll();
		List<Produto> retorno = new ArrayList<Produto>();
		if (produtos.isEmpty())
			throw new ObjetoInexistenteException("Nenhum produto cadastrado");
		for (Produto produto : produtos) {
			List<Lote> lotes = findAllLotesValidos(produto);
			if (lotes.isEmpty()) {
				produtoService.invalidarProduto(produto);
			}else {
				retorno.add(produto);
			}
		}
		return retorno;
	}

	private List<Lote> findAllLotesValidos(Produto produto) throws Exception {
		List<Lote> lotes = loteRepository.findAllByProduto(produto);
		List<Lote> retorno = new ArrayList<Lote>();
		if (lotes.isEmpty()) {
			throw new ObjetoInexistenteException("Lote de Produto não cadastrado");
		}
		DateFormat newStyle1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat newStyle2 = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String hoje = newStyle1.format(date);
		for (Lote lote : lotes) {
			Date dataValidade = newStyle2.parse((lote.getDataDeValidade()).replaceAll("/", "-"));
			long diferencaDias = (dataValidade.getTime() - newStyle1.parse(hoje).getTime()) / (1000 * 60 * 60 * 24);
			if (diferencaDias >= 0) {
				retorno.add(lote);
			}
		}
		return retorno;
	}
}
