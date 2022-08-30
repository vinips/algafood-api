package com.vinips.algafood.api;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.controller.CidadeController;
import com.vinips.algafood.api.controller.CozinhaController;
import com.vinips.algafood.api.controller.EstadoController;
import com.vinips.algafood.api.controller.FormaPagamentoController;
import com.vinips.algafood.api.controller.PedidoController;
import com.vinips.algafood.api.controller.RestauranteController;
import com.vinips.algafood.api.controller.RestauranteProdutoController;
import com.vinips.algafood.api.controller.RestauranteUsuarioResponsavelController;
import com.vinips.algafood.api.controller.UsuarioController;
import com.vinips.algafood.api.controller.UsuarioGrupoController;

@Component
public class AlgaLinks {
	
	private final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	public Link linkToPedidos() {
		
		TemplateVariables fieldVariables = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
		
		String pedidosURL = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();
		
		return new Link(UriTemplate.of(pedidosURL, PAGINACAO_VARIABLES.concat(fieldVariables)), "pedidos");
	}
	
	public Link linkToRestauranteUsuarioResponsavel(Long restauranteId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class).listar(restauranteId)).withSelfRel();
	}
	
	public Link linkToCidades() {
		return WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel();
	}

	public Link linkToCidades(String rel) {
		return WebMvcLinkBuilder.linkTo(CidadeController.class).withRel(rel);
	}
	
	public Link linkToCidade(Long cidadeId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscar(cidadeId)).withSelfRel();
	}
	
	public Link linkToCozinhas(String rel) {
		return WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel(rel);
	}

	public Link linkToEstado(Long estadoId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).buscar(estadoId)).withSelfRel();
	}
	
	public Link linkToEstados() {
		return WebMvcLinkBuilder.linkTo(EstadoController.class).withSelfRel();
	}
	
	public Link linkToEstados(String rel) {
		return WebMvcLinkBuilder.linkTo(EstadoController.class).withRel(rel);
	}
	
	public Link linkToRestaurante(Long restauranteId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).buscar(restauranteId)).withSelfRel();
	}
	
	public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
		return WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId))
				.withRel(rel);
	}
	
	public Link linkToUsuario(Long usuarioId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscar(usuarioId)).withSelfRel();
	}
	
	public Link linkToUsuarios(String rel) {
		return WebMvcLinkBuilder.linkTo(UsuarioController.class).withRel(rel);
	}
	
	public Link linkToUsuarios() {
		return WebMvcLinkBuilder.linkTo(UsuarioController.class).withSelfRel();
	}
	
	public Link linkToFormaPagamento(Long formaPagamentoId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FormaPagamentoController.class).buscar(formaPagamentoId)).withSelfRel();
	}
	
	public Link linkToUsuarioGrupo(Long usuarioId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class).listar(usuarioId))
				.withRel(rel);
	}
	
}
