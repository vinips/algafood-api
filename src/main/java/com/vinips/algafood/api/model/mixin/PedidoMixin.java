package com.vinips.algafood.api.model.mixin;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinips.algafood.domain.model.Endereco;
import com.vinips.algafood.domain.model.ItemPedido;

public abstract class PedidoMixin {
	
	// Explicação no RestauranteMixin

	@JsonIgnore
	private LocalDateTime dataCriacao;
	
	@JsonIgnore
	private LocalDateTime dataConfirmacao;
	
	@JsonIgnore
	private LocalDateTime dataCancelamento;
	
	@JsonIgnore
	private LocalDateTime dataEntrega;
	
	@JsonIgnore
	private Endereco enderecoEntrega;
	
	@JsonIgnore
	private List<ItemPedido> itens;

}
