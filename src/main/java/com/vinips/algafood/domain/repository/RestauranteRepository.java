package com.vinips.algafood.domain.repository;

import java.util.List;

import com.vinips.algafood.domain.model.Restaurante;

public interface RestauranteRepository {
	
	List<Restaurante> listar();
	Restaurante buscar(Long id);
	Restaurante salvar(Restaurante restaurante);
	void remover (Long id);
	

}
