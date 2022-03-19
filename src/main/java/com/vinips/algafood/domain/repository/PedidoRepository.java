package com.vinips.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.vinips.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>{
	

}
	