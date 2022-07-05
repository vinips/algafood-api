package com.vinips.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Essa classe serve para colocarmos as anotações do Jackson nelas, em vez de nas entidades, fazendo assim com que o código fique mais limpo e legível.
 * Paramos de utilizar o Mixin quando passamos a usar DTO e Input para representar as entidades pro cliente. Então essa classe é depreciada. 
 */

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
//		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
//		setMixInAnnotation(Cidade.class, CidadeMixin.class);
//		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
//		setMixInAnnotation(Grupo.class, GrupoMixin.class);
//		setMixInAnnotation(Pedido.class, PedidoMixin.class);
//		setMixInAnnotation(Usuario.class, UsuarioMixin.class);
	}
	
	

}
