package com.vinips.algafood.api.model.mixin;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinips.algafood.domain.model.Grupo;

public abstract class UsuarioMixin {
	
	// Explicação no RestauranteMixin

	@JsonIgnore
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	private List<Grupo> grupos;

}
