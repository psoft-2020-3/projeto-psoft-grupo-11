package com.ufcg.psoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.model.DTO.PrecoSituacaoDTO;
import com.ufcg.psoft.model.DTO.ProdutoInputDTO;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.service.ProdutoServiceImpl;
import com.ufcg.psoft.util.CustomErrorType;

import exceptions.ObjetoInvalidoException;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({ "/produto" })
@CrossOrigin(value = "*")
public class ProdutoController {

	@Autowired
	ProdutoServiceImpl produtoService;

	@RequestMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ApiOperation(value = "Insere um produto")
	public ResponseEntity<Produto> save(@RequestBody ProdutoInputDTO produto) throws ObjetoInvalidoException {
		Produto produtoSalvo = this.produtoService.save(produto);
		return new ResponseEntity<Produto>(produtoSalvo, HttpStatus.CREATED);
	}

	// ● Eu, como cliente, gostaria de consultar a disponibilidade e o preço de cada
	// produto do supermercado (não precisa estar logado)
	@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ApiOperation(value = "Retorna todos os produtos e seus preços")
	public ResponseEntity<List<PrecoSituacaoDTO>> findAll() {
		List<PrecoSituacaoDTO> produtos = this.produtoService.findPriceAndSituation();
		return new ResponseEntity<List<PrecoSituacaoDTO>>(produtos, HttpStatus.OK);
	}

	// ORDENAR PRODUTOS POR INFORMACOES IMPORTANTES
	@RequestMapping(value = "/orderBy/{field}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ApiOperation(value = "Retorna todos os produtos ordenados pelo parametro passado")
	public ResponseEntity<List<Produto>> findAllOrdered(@PathVariable String field) {
		List<Produto> produtos = this.produtoService.findAllOrdered(field);
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	// PRIDUTOS INDISPONIVEIS
	@RequestMapping(value = "/unavailable", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ApiOperation(value = "Retorna todos os produtos indisponiveis (lista de indisponiveis)")
	public ResponseEntity<List<Produto>> findAllUnavailable() {
		List<Produto> produtos = this.produtoService.findAllUnavailable();
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ApiOperation(value = "Retorna um produto pelo id")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		try {
			Produto produto = produtoService.findById(id);
			return new ResponseEntity<Produto>(produto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
	}

}
