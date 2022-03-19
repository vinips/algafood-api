package com.vinips.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

//Esse Custom é uma classe nossa que extende o Repositorio padrao do Spring. Nós implementamos as coisas dele no CustomJpaRepositoryImpl
@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID>{

	Optional<T> buscarPrimeiro();
	
	void detach(T entity);
}
