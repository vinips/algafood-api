package com.vinips.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.vinips.algafood.api.model.mixin.CidadeMixin;
import com.vinips.algafood.api.model.mixin.CozinhaMixin;
import com.vinips.algafood.api.model.mixin.GrupoMixin;
import com.vinips.algafood.api.model.mixin.PedidoMixin;
import com.vinips.algafood.api.model.mixin.RestauranteMixin;
import com.vinips.algafood.api.model.mixin.UsuarioMixin;
import com.vinips.algafood.domain.model.Cidade;
import com.vinips.algafood.domain.model.Cozinha;
import com.vinips.algafood.domain.model.Grupo;
import com.vinips.algafood.domain.model.Pedido;
import com.vinips.algafood.domain.model.Restaurante;
import com.vinips.algafood.domain.model.Usuario;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
		setMixInAnnotation(Grupo.class, GrupoMixin.class);
		setMixInAnnotation(Pedido.class, PedidoMixin.class);
		setMixInAnnotation(Usuario.class, UsuarioMixin.class);
	}
	
	

}
