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

import com.ufcg.psoft.model.DTO.LoteInputDTO;
import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.service.LoteServiceImpl;
import com.ufcg.psoft.util.CustomErrorType;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({ "/lote" })
@CrossOrigin(value = "*")
public class LoteController {

	@Autowired
	LoteServiceImpl loteService;

	@RequestMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ApiOperation(value = "Insere um lote")
	public ResponseEntity<?> save(@RequestBody LoteInputDTO lote) {
		try {
			Lote loteSalvo = this.loteService.save(lote);
			return new ResponseEntity<Lote>(loteSalvo, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<CustomErrorType>(
					new CustomErrorType("Produto with id " + lote.getIdProduto() + " not found"), HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Lista todos os lotes")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> findAll() {
		List<Lote> lotes = loteService.findAll();

		if (lotes.isEmpty()) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Não existe nenhum lote"), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todos os lotes do produto")
	@RequestMapping(value = "/produto/{idProduto}", method = RequestMethod.GET)
	public ResponseEntity<?> findByProduct(@PathVariable Long idProduto) {
		List<Lote> lotes = loteService.findAllLotesByProduto(idProduto);

		if (lotes.isEmpty()) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Não existe nenhum lote desse produto"), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}
}
