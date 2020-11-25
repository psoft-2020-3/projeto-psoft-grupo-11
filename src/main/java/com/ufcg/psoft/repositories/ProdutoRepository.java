package com.ufcg.psoft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ufcg.psoft.model.DTO.PrecoSituacaoDTO;
import com.ufcg.psoft.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findByCodigoBarra(String codigoBarra);

	// ● Eu, como cliente, gostaria de consultar a disponibilidade e o preço de cada
	// produto do supermercado (não precisa estar logado)
	@Query(value = "SELECT nome, preco, Cast('Disponível' as varchar) as situacao FROM Produto WHERE situacao = 1", nativeQuery = true)
	List<PrecoSituacaoDTO> findPriceAndSituationAvailable();
	
	// ● Eu, como cliente, gostaria de consultar a disponibilidade e o preço de cada
	// produto do supermercado (não precisa estar logado)
	@Query(value = "SELECT nome, Cast('N/A' as varchar) as preco, Cast('Indisponível' as varchar) as situacao FROM Produto WHERE situacao = 2", nativeQuery = true)
	List<PrecoSituacaoDTO> findPriceAndSituationUnavailable();
	
	// PRIDUTOS INDISPONIVEIS
	@Query(value = "SELECT * FROM Produto WHERE situacao = 2", nativeQuery = true)
	List<Produto> findAllUnavailable();
	
	@Query(value = "SELECT * FROM Produto WHERE situacao = 1 AND id IN ?1", nativeQuery = true)
	List<Produto> findProductsCompra(List<Long> ids);

}