package com.ufcg.psoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ufcg.psoft.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

	// RELATORIO GERAL
	@Query(value = "SELECT SUM(valor_total) FROM venda", nativeQuery = true)
	Double calculateProfit();
}
