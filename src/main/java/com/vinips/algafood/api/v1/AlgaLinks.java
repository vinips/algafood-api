package com.vinips.algafood.api.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.v1.controller.CidadeController;
import com.vinips.algafood.api.v1.controller.CozinhaController;
import com.vinips.algafood.api.v1.controller.EstadoController;
import com.vinips.algafood.api.v1.controller.EstatisticasController;
import com.vinips.algafood.api.v1.controller.FormaPagamentoController;
import com.vinips.algafood.api.v1.controller.GrupoController;
import com.vinips.algafood.api.v1.controller.GrupoPermissaoController;
import com.vinips.algafood.api.v1.controller.PedidoController;
import com.vinips.algafood.api.v1.controller.PermissaoController;
import com.vinips.algafood.api.v1.controller.RestauranteController;
import com.vinips.algafood.api.v1.controller.RestauranteFormaPagamentoController;
import com.vinips.algafood.api.v1.controller.RestauranteFotoProdutoController;
import com.vinips.algafood.api.v1.controller.RestauranteProdutoController;
import com.vinips.algafood.api.v1.controller.RestauranteUsuarioResponsavelController;
import com.vinips.algafood.api.v1.controller.UsuarioController;
import com.vinips.algafood.api.v1.controller.UsuarioGrupoController;

@Component
public class AlgaLinks {
	
	private final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	public Link linkToPedidos(String rel) {
		
		TemplateVariables fieldVariables = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
		
		String pedidosURL = linkTo(PedidoController.class).toUri().toString();
		
		return new Link(UriTemplate.of(pedidosURL, PAGINACAO_VARIABLES.concat(fieldVariables)), rel);
	}
	
	public Link linkToCidades() {
		return this.linkToCidades(IanaLinkRelations.SELF.value());
	}

	public Link linkToCidades(String rel) {
		return linkTo(CidadeController.class).withRel(rel);
	}
	
	public Link linkToCidade(Long cidadeId) {
		return linkTo(methodOn(CidadeController.class).buscar(cidadeId)).withSelfRel();
	}
	
	public Link linkToCozinhas(String rel) {
		return linkTo(CozinhaController.class).withRel(rel);
	}
	
	public Link linkToCozinha(Long cozinhaId) {
		return linkTo(methodOn(CozinhaController.class).buscar(cozinhaId)).withSelfRel();
	}

	public Link linkToEstado(Long estadoId) {
		return linkTo(methodOn(EstadoController.class).buscar(estadoId)).withSelfRel();
	}
	
	public Link linkToEstados() {
		return this.linkToEstados(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToEstados(String rel) {
		return linkTo(EstadoController.class).withRel(rel);
	}
	
	public Link linkToUsuario(Long usuarioId) {
		return linkTo(methodOn(UsuarioController.class).buscar(usuarioId)).withSelfRel();
	}
	
	public Link linkToUsuarios(String rel) {
		return linkTo(UsuarioController.class).withRel(rel);
	}
	
	public Link linkToUsuarios() {
		return this.linkToUsuarios(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToFormaPagamento(Long formaPagamentoId) {
		return linkTo(methodOn(FormaPagamentoController.class).buscar(formaPagamentoId)).withSelfRel();
	}
	
	public Link linkToFormasPagamento(String rel) {
		return linkTo(FormaPagamentoController.class).withRel(rel);
	}
	
	public Link linkToFormasPagamento() {
		return this.linkToFormasPagamento(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToUsuarioGrupo(Long usuarioId, String rel) {
		return linkTo(methodOn(UsuarioGrupoController.class).listar(usuarioId))
				.withRel(rel);
	}
	
	public Link linkToUsuarioGrupo(Long usuarioId) {
		return this.linkToUsuarioGrupo(usuarioId, IanaLinkRelations.SELF.value());
	}
	
	// O HATEOAS é inteligente o suficiente para saber que quando o parametro é null, 
	// ele coloca o nome do parametro entre {} na URL, exemplo {grupoId}
	public Link linkToUsuarioGrupoAssociacao(Long usuarioId, String rel) {
		return linkTo(methodOn(UsuarioGrupoController.class).associar(usuarioId, null))
				.withRel(rel);
	}
	
	public Link linkToUsuarioGrupoDesasociacao(Long usuarioId, Long grupoId, String rel) {
		return linkTo(methodOn(UsuarioGrupoController.class).associar(usuarioId, grupoId))
				.withRel(rel);
	}
	
	public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(PedidoController.class).confirmar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToEntregaPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(PedidoController.class).entregar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(PedidoController.class).cancelar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToRestaurantes(String rel) {
		TemplateVariables fieldVariables = new TemplateVariables(
				new TemplateVariable("projecao", VariableType.REQUEST_PARAM));
		
		String restaurantesURL = linkTo(RestauranteController.class).toUri().toString();
		
		return new Link(UriTemplate.of(restaurantesURL, fieldVariables), rel);
	}

	public Link linkToRestaurantes() {
		return this.linkToRestaurantes(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestaurante(Long restauranteId) {
		return linkTo(methodOn(RestauranteController.class).buscar(restauranteId)).withSelfRel();
	}
	
	public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).ativar(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestauranteInativacao(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).inativar(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestauranteAbertura(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).abrir(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestauranteFechamento(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).fechar(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteFormaPagamentoController.class).listar(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestauranteFormasPagamento(Long restauranteId) {
		return this.linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestauranteFormaPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId, String rel) {
		return linkTo(methodOn(RestauranteFormaPagamentoController.class).desassociar(restauranteId, formaPagamentoId)).withRel(rel);
	}
	
	public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteFormaPagamentoController.class).associar(restauranteId, null)).withRel(rel);
	}
	
	public Link linkToRestauranteUsuarioResponsavel(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).listar(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestauranteUsuarioResponsavel(Long restauranteId) {
		return this.linkToRestauranteUsuarioResponsavel(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestauranteUsuarioResponsavelDesassociacao(Long restauranteId, Long usuarioId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).desassociar(restauranteId, usuarioId)).withRel(rel);
	}
	
	public Link linkToRestauranteUsuarioResponsavelAssociacao(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).associar(restauranteId, null)).withRel(rel);
	}
	
	public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
		return linkTo(methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId))
				.withRel(rel);
	}
	
	public Link linkToProdutos(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteProdutoController.class).listar(restauranteId, null)).withRel(rel);
	}
	
	public Link linkToProdutos(Long restauranteId) {
		return this.linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestauranteFotoProduto(Long restauranteId, Long produtoId, String rel) {
		return linkTo(methodOn(RestauranteFotoProdutoController.class).buscarFoto(restauranteId, produtoId)).withRel(rel);
	}
	
	public Link linkToGrupos(String rel) {
		return linkTo(GrupoController.class).withRel(rel);
	}
	
	public Link linkToGrupos() {
		return this.linkToGrupos(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToGrupoPermissoes(Long grupoId, String rel) {
		return linkTo(methodOn(GrupoPermissaoController.class).listar(grupoId)).withRel(rel);
	}
	
	public Link linkToGrupoPermissoes(Long grupoId) {
		return this.linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToGrupoPermissaoAssociacao(Long grupoId, String rel) {
		return linkTo(methodOn(GrupoPermissaoController.class).associar(grupoId, null)).withRel(rel);
	}
	
	public Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String rel) {
		return linkTo(methodOn(GrupoPermissaoController.class).desassociar(grupoId, permissaoId)).withRel(rel);
	}
	
	public Link linkToPermissoes(String rel) {
		return linkTo(PermissaoController.class).withRel(rel);
	}
	
	public Link linkToPermissoes() {
		return this.linkToPermissoes(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToEstatisticas(String rel) {
		return linkTo(EstatisticasController.class).withRel(rel);
	}
	
	public Link linkToEstatisticasVendasDiarias(String rel) {
		TemplateVariables fieldVariables = new TemplateVariables(
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM),
				new TemplateVariable("timeOffSet", VariableType.REQUEST_PARAM));

		String vendasDiariasURL = linkTo(methodOn(EstatisticasController.class).consultarVendasDiarias(null, null))
				.toUri().toString();

		return new Link(UriTemplate.of(vendasDiariasURL, fieldVariables), rel);
	}
	
	
}
