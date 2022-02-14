package com.vinips.algafood.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinips.algafood.domain.model.Restaurante;

public abstract class CozinhaMixin {
	
	// Explicação no RestauranteMixin

	@JsonIgnore
	private List<Restaurante> restaurantes = new ArrayList<>();	

}
