package com.ufcg.psoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.model.DTO.RelatorioDTO;
import com.ufcg.psoft.service.RelatorioService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/relatorio")
@CrossOrigin
public class RestApiController {

	@Autowired
	RelatorioService relatorioService;

	// RELATORIO GERAL
	@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ApiOperation(value = "Exibe o relatorio geral")
	public ResponseEntity<RelatorioDTO> relatorio() {
		RelatorioDTO relatorio = this.relatorioService.gerarRelatorio();
		return new ResponseEntity<RelatorioDTO>(relatorio, HttpStatus.OK);
	}

}
