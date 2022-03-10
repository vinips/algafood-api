package com.vinips.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vinips.algafood.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);

	//Outro jeito de fazer
//	@Query("from Produto where restaurante.id = :restaurante and id = :produto")
//	Optional<Produto> findById(@Param("restaurante") Long restauranteId, @Param("produto") Long produtoId);

}
