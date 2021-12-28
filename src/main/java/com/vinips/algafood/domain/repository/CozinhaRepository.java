package com.vinips.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vinips.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {
	
	List<Cozinha> findListaByNomeContaining(String nome);

	Cozinha findUnicaByNome(String nome);
	
	boolean existsByNomeContains(String nome);

}
