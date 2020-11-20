package com.ufcg.psoft.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;

public interface LoteRepository extends JpaRepository<Lote, Long>{
	Optional<Lote> findByProduto(Produto produto);
	List<Lote> findAllByProduto(Produto produto);
}