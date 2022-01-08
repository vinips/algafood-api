package com.vinips.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.vinips.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
		JpaSpecificationExecutor<Restaurante> {
	
	//@Query("SELECT DISTINCT r FROM Restaurante r JOIN r.cozinha LEFT JOIN FETCH r.formasPagamento")
	//List<Restaurante> findAll();

	// Usamos o FindBy para procurar, podemos usar SteamBy, getBy, etc. Between é
	// palavra chave.
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

	// @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, Long id);

	// Containing é o Like e o And usa igual a sql
	// List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long id);

	// First usado para primeiro resultado
	Optional<Restaurante> findFirstByNomeContaining(String nome);

	// Top2 para os 2 primeiros
	List<Restaurante> findTop2ByNomeContaining(String nome);

	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
