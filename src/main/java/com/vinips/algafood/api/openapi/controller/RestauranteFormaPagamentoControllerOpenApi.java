package com.vinips.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.model.dto.FormaPagamentoDTO;

import io.swagger.annotations.Api;
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
@Api(tags = "Restaurantes") //Essa tags é o mesmo que especificamos no apiDocket da classe SpringFoxConfig
public interface RestauranteFormaPagamentoControllerOpenApi {
	
	@ApiOperation("Lista as Formas de Pagamento vinculadas a um Restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do Restaurante inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CollectionModel<FormaPagamentoDTO> listar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId);

	@ApiOperation("Associa uma Forma de Pagamento a um Restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Forma de Pagamento vinculada ao Restaurante"),
		@ApiResponse(responseCode = "404", description = "Restaurante ou Forma de Pagamento não encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> associar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId,
			@ApiParam(value = "ID de uma Forma de Pagamento", example = "1") Long formaPagamentoId);
	
	@ApiOperation("Desassocia uma Forma de Pagamento de um Restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Forma de Pagamento desvinculada do Restaurante"),
		@ApiResponse(responseCode = "404", description = "Restaurante ou Forma de Pagamento não encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> desassociar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId,
			@ApiParam(value = "ID de uma Forma de Pagamento", example = "1") Long formaPagamentoId);

	
}
