package com.ufcg.psoft.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ufcg.psoft.model.CategoriaDesconto;


public interface CategoriaDescontoRepository extends JpaRepository<CategoriaDesconto, Long> {

	CategoriaDesconto findByCategoria(String categoria);

	@Transactional
	@Modifying
	@Query(value = "UPDATE categoria_desconto SET desconto = ?2 WHERE categoria = ?1", nativeQuery = true)
	void update(String categoria, String desconto);
}
