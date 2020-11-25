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

import com.ufcg.psoft.model.Venda;
import com.ufcg.psoft.model.DTO.VendaInputDTO;
import com.ufcg.psoft.service.VendaService;
import com.ufcg.psoft.util.CustomErrorType;

import exceptions.ObjetoInvalidoException;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping({ "/venda" })
@CrossOrigin(value = "*")
public class VendaController {

	@Autowired
	VendaService vendaService;

	// CRIA UMA VENDA
	@RequestMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ApiOperation(value = "Cria uma venda")
	public ResponseEntity<?> save(@RequestBody VendaInputDTO venda) {
		Venda vendaSalva;
		try {
			vendaSalva = this.vendaService.save(venda);
			return new ResponseEntity<>(vendaSalva, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ApiOperation(value = "Lista todas as vendas")
	public ResponseEntity<List<Venda>> findAll() {
		List<Venda> vendas = this.vendaService.findAll();
		return new ResponseEntity<List<Venda>>(vendas, HttpStatus.OK);
	}
	
	// ORDENAR VENDA POR INFORMACOES IMPORTANTES
	@RequestMapping(value = "/orderBy/{field}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ApiOperation(value = "Lista todas as vendas")
	public ResponseEntity<List<Venda>> findAllOrdered(@PathVariable String field) {
		List<Venda> vendas = this.vendaService.findAllOrdered(field);
		return new ResponseEntity<List<Venda>>(vendas, HttpStatus.OK);
	}

	// DELETA VENDA
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	@ApiOperation(value = "Deleta uma venda")
	public ResponseEntity<HttpStatus> delete(@PathVariable Long id) throws ObjetoInvalidoException {
		this.vendaService.delete(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
}
