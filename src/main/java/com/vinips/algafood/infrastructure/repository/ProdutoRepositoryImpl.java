package com.vinips.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.vinips.algafood.domain.model.FotoProduto;
import com.vinips.algafood.domain.service.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries{

	@PersistenceContext
	private EntityManager manager;

	@Override
	public FotoProduto save(FotoProduto foto) {
		return manager.merge(foto);
	}
	
}
