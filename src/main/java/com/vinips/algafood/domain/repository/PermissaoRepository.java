package com.vinips.algafood.domain.repository;

import java.util.List;

import com.vinips.algafood.domain.model.Permissao;

public interface PermissaoRepository {
	
	List<Permissao> listar();
	Permissao buscar(Long id);
	Permissao salvar(Permissao permissao);
	void remover (Long id);
	

}
