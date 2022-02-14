package com.vinips.algafood.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinips.algafood.domain.model.Permissao;

public abstract class GrupoMixin {
	
	// Explicação no RestauranteMixin

	@JsonIgnore
	private List<Permissao> permissoes = new ArrayList<>();

}
