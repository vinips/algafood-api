package com.vinips.algafood.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.vinips.algafood.domain.filter.VendaDiariaFilter;
import com.vinips.algafood.domain.model.Pedido;
import com.vinips.algafood.domain.model.StatusPedido;
import com.vinips.algafood.domain.model.dto.VendaDiaria;
import com.vinips.algafood.domain.service.VendaQueryService;

//Poderia ser um @Component, mas o Repository nesse caso cabe melhor.
@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String timeOffSet) {
		var builder = entityManager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);
		
		//Essa função converte o offset da data de UTF (padrão) para o offSet que passarmos
		var functionConvertTzDataCriacao = builder.function(
				"convert_tz", 
				Date.class, 
				root.get("dataCriacao"),
				builder.literal("+00:00"), 
				builder.literal(timeOffSet));
		
		var functionDateDataCriacao = builder.function(
				"date", 
				Date.class, 
				functionConvertTzDataCriacao);
		
		var selection = builder.construct(VendaDiaria.class, 
				functionDateDataCriacao,
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		
		query.select(selection);
		query.groupBy(functionDateDataCriacao);
		
		//WHERE
		var predicates = new ArrayList<Predicate>();
		
		if(filter.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
		}
		
		if(filter.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
		}
		
		if(filter.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
		}
		
		List<StatusPedido> statusPermitidos = new ArrayList<StatusPedido>();
		statusPermitidos.add(StatusPedido.CONFIRMADO);
		statusPermitidos.add(StatusPedido.ENTREGUE);
		
		predicates.add(root.get("status").in(statusPermitidos));
		
		//ADICIONA O WHERE NA QUERY
		query.where(predicates.toArray(new Predicate[0]));
		
		return entityManager.createQuery(query).getResultList();
	}

}
