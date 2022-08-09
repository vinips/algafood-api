package com.vinips.algafood.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.model.dto.PedidoDTO;
import com.vinips.algafood.api.model.dto.PedidoResumoDTO;
import com.vinips.algafood.api.model.input.PedidoInput;
import com.vinips.algafood.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Essa Classe serve para colocarmos as anotações de documentação (Swagger) do controlador.
 * Fazendo com que assim o controlador não fique tão poluído de informações
 */

//Marca esse controlador como um recurso do Swagger, que faz automaticamente a documentação da nossa API.  Módulo 18
@Api(tags = "Pedidos") //Essa tags é o mesmo que especificamos no apiDocket da classe SpringFoxConfig
public interface PedidoControllerOpenApi {
	
	//Essa anotação faz com que em vez de aparecer o nome do método, criado automaticamente pelo Swagger, na documentação, apareça o que determinarmos nessa anotação.
	@ApiOperation("Pesquisa Pedidos")
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula", name = "campos", paramType = "query", type = "string")
	})
	public PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable);

	
	@ApiOperation("Busca um Pedido pelo código")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public PedidoDTO buscar(@ApiParam(value = "Código de um Pedido", example = "f55a5397-abba-11ec-9203-b42e99ed2795") String codigoPedido);
	
	@ApiOperation("Registra um Pedido")
	public PedidoDTO adicionar(PedidoInput pedidoInput);
	
	@ApiOperation("Confirma um Pedido criado")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Pedido confirmado"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void confirmar(@ApiParam(value = "Código de um Pedido", example = "f55a5397-abba-11ec-9203-b42e99ed2795") String codigoPedido);
	
	@ApiOperation("Entrega um Pedido confirmado")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Pedido entregue"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void entregar(@ApiParam(value = "Código de um Pedido", example = "f55a5397-abba-11ec-9203-b42e99ed2795") String codigoPedido);
	
	@ApiOperation("Cancela um Pedido criado")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Pedido cancelado"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void cancelar(@ApiParam(value = "Código de um Pedido", example = "f55a5397-abba-11ec-9203-b42e99ed2795") String codigoPedido);
	
	

}
