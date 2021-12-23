package com.vinips.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.vinips.algafood.domain.model.FormaPagamento;
import com.vinips.algafood.domain.repository.FormaPagamentoRepository;

@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository{

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<FormaPagamento> listar(){
		return manager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
	}
	
	@Override
	public FormaPagamento buscar(Long id) {
		return manager.find(FormaPagamento.class, id);
	}
	
	@Override
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return manager.merge(formaPagamento);
	}
	
	@Override
	@Transactional
	public void remover(Long id) {
		FormaPagamento formaPagamento = this.buscar(id);
		
		if(formaPagamento == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove(formaPagamento);
	}
	
	

}
