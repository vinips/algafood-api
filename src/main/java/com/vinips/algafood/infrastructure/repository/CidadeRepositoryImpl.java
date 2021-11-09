package com.vinips.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.vinips.algafood.domain.model.Cidade;
import com.vinips.algafood.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository{

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Cidade> listar(){
		return manager.createQuery("from Cidade", Cidade.class).getResultList();
	}
	
	@Override
	public Cidade buscar(Long id) {
		return manager.find(Cidade.class, id);
	}
	
	@Override
	@Transactional
	public Cidade salvar(Cidade cidade) {
		return manager.merge(cidade);
	}
	
	@Override
	public void remover(Cidade cidade) {
		cidade = this.buscar(cidade.getId());
		manager.remove(cidade);
	}
	
	

}
