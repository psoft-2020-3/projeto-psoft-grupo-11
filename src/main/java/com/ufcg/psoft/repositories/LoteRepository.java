package com.ufcg.psoft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;


public interface LoteRepository extends JpaRepository<Lote, Long> {
	List<Lote> findByProduto(Produto produto);

	List<Lote> findAllByProduto(Produto produto);

	@Query(value = "SELECT SUM(numero_de_itens) FROM Lote WHERE id_produto = ?1", nativeQuery = true)
	Integer countItensByProduct(Long idProduto);
}