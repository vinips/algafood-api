package com.vinips.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vinips.algafood.domain.model.FotoProduto;
import com.vinips.algafood.domain.model.Produto;
import com.vinips.algafood.domain.model.Restaurante;
import com.vinips.algafood.domain.service.ProdutoRepositoryQueries;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {

	Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);
	
	List<Produto> findAllByRestaurante(Restaurante restaurante);
	
	@Query("From Produto p where p.ativo = true and p.restaurante = :restaurante")
	List<Produto> findAtivosByRestaurante(Restaurante restaurante);

	//Outro jeito de fazer
//	@Query("from Produto where restaurante.id = :restaurante and id = :produto")
//	Optional<Produto> findById(@Param("restaurante") Long restauranteId, @Param("produto") Long produtoId);

	@Query("From FotoProduto f where f.produto.restaurante.id = :restauranteId and f.produto.id = :produtoId")
	Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);
}
