package com.vinips.algafood.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinips.algafood.domain.model.Grupo;

public abstract class UsuarioMixin {
	
	// Explicação no RestauranteMixin

	@JsonIgnore
	private OffsetDateTime dataCadastro;
	
	@JsonIgnore
	private List<Grupo> grupos;

}
