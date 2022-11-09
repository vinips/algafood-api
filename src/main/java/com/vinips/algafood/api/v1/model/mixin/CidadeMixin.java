package com.vinips.algafood.api.v1.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vinips.algafood.domain.model.Estado;

public abstract class CidadeMixin {
	
	// Explicação no RestauranteMixin

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;

}
