package com.vinips.algafood.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.vinips.algafood.domain.filter.PedidoFilter;
import com.vinips.algafood.domain.model.Pedido;

public class PedidoSpecs {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
		return (root, query, builder) -> {
			//Quando implementamos a Paginação e o Spec no mesmo endpoing, o Sping faz a query de paginação nesse mesmo método aqui.
			//Para evitar um erro de fetch quando entrar aqui com a query de count, fazemos esse if para fazer o fetch apenas
			//Se o resultado da query for um tipo Pedido, o count retorna um int.
			if(Pedido.class.equals(query.getResultType())) {
				//Para resolver o problema de N + 1
				//é como se estivessemos fazendo "from Pedido p join fetch p.restaurante r join fetch r.cozinha
				root.fetch("restaurante").fetch("cozinha");
				root.fetch("cliente");
			}
			
			var predicates = new ArrayList<Predicate>();
			
			if(filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
			}
			
			if(filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
			}
			
			if(filtro.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
			}
			
			if(filtro.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
