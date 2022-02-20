package com.vinips.algafood.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vinips.algafood.domain.model.Cozinha;
import com.vinips.algafood.domain.model.Endereco;
import com.vinips.algafood.domain.model.FormaPagamento;
import com.vinips.algafood.domain.model.Produto;

public abstract class RestauranteMixin {

	// Pra funcionar, precisa criar um componente que Estende SimpleModule. 
	// E lá dentro linkar o Mixin a Entidade. No caso é JacksonMixinModule.

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Cozinha cozinha;

	@JsonIgnore
	private Endereco endereco;

	@JsonIgnore
	private OffsetDateTime dataCadastro;

	@JsonIgnore
	private OffsetDateTime dataAtualizacao;

	@JsonIgnore
	private List<FormaPagamento> formasPagamento;

	@JsonIgnore
	private List<Produto> produtos;
	
}
