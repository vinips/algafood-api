package com.vinips.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vinips.algafood.domain.model.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
	

}
