package com.vinips.algafood.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinips.algafood.domain.model.Endereco;
import com.vinips.algafood.domain.model.ItemPedido;

public abstract class PedidoMixin {
	
	// Explicação no RestauranteMixin

	@JsonIgnore
	private OffsetDateTime dataCriacao;
	
	@JsonIgnore
	private OffsetDateTime dataConfirmacao;
	
	@JsonIgnore
	private OffsetDateTime dataCancelamento;
	
	@JsonIgnore
	private OffsetDateTime dataEntrega;
	
	@JsonIgnore
	private Endereco enderecoEntrega;
	
	@JsonIgnore
	private List<ItemPedido> itens;

}
