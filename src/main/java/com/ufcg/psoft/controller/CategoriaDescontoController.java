package com.ufcg.psoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.model.CategoriaDesconto;
import com.ufcg.psoft.service.CategoriaDescontoService;
import com.ufcg.psoft.util.CustomErrorType;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping({ "/categoriaDesconto" })
@CrossOrigin(value = "*")
public class CategoriaDescontoController {

	@Autowired
	CategoriaDescontoService categoriaDescontoService;

	@RequestMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ApiOperation(value = "Adiciona desconto à uma categoria")
	public ResponseEntity<?> save(@RequestBody CategoriaDesconto categoriaDesconto) {
		try {
			CategoriaDesconto categoriaDescontoSalva = this.categoriaDescontoService.save(categoriaDesconto);
			return new ResponseEntity<CategoriaDesconto>(categoriaDescontoSalva, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Altera desconto de uma categoria")
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestParam String categoria, @RequestParam String desconto) {
		try {
			CategoriaDesconto categoriaDesconto = this.categoriaDescontoService.update(categoria, desconto);

			return new ResponseEntity<CategoriaDesconto>(categoriaDesconto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}
}
