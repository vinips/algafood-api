package com.vinips.algafood.domain.repository;

import java.util.List;

import com.vinips.algafood.domain.model.Cozinha;

public interface CozinhaRepository {
	
	List<Cozinha> listar();
	Cozinha buscar(Long id);
	Cozinha salvar(Cozinha cozinha);
	void remover (Long id);
	

}
