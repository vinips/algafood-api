package com.vinips.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.v1.model.dto.UsuarioDTO;

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
public interface RestauranteUsuarioResponsavelControllerOpenApi {
	
	@ApiOperation("Lista os Responsáveis vinculados a um Restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do Restaurante inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CollectionModel<UsuarioDTO> listar(@ApiParam(value = "ID de um Restaurante", example = "1")  Long restauranteId);

	@ApiOperation("Associa um Responsável a um Restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Responsável vinculado ao Restaurante"),
		@ApiResponse(responseCode = "404", description = "Restaurante ou Responsável não encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> associar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId,
			@ApiParam(value = "ID de um Responsável", example = "1") Long usuarioId);
	
	@ApiOperation("Desassocia um Responsável de um Restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Responsável desvinculado do Restaurante"),
		@ApiResponse(responseCode = "404", description = "Restaurante ou Responsável não encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> desassociar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId,
			@ApiParam(value = "ID de um Responsável", example = "1") Long usuarioId);

	
}
