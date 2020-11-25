package com.ufcg.psoft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ufcg.psoft.model.VendaProduto;


@Repository
public interface VendaProdutoRepository extends JpaRepository<VendaProduto, Long> {

	@Query(value = "SELECT * FROM venda_produto WHERE id_venda = ?1", nativeQuery = true)
	List<VendaProduto> findAllByIdVenda(Long idVenda);
}
