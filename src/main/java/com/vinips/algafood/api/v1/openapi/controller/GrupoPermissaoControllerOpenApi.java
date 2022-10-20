package com.vinips.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.v1.model.dto.PermissaoDTO;

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
@Api(tags = "Grupos") //Essa tags é o mesmo que especificamos no apiDocket da classe SpringFoxConfig
public interface GrupoPermissaoControllerOpenApi {
	
	@ApiOperation("Lista as Permissões vinculadas a um Grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do Grupo inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CollectionModel<PermissaoDTO> listar(@ApiParam(value = "ID de um Grupo", example = "1") Long grupoId);

	@ApiOperation("Associa uma Permissão a um Grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Permissão vinculada ao Grupo"),
		@ApiResponse(responseCode = "404", description = "Grupo ou Permissão não encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> associar(@ApiParam(value = "ID de um Grupo", example = "1") Long grupoId,
			@ApiParam(value = "ID de uma Permissão", example = "1") Long permissaoId);
	
	@ApiOperation("Desassocia uma Permissão de um Grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Permissão desvinculada do Grupo"),
		@ApiResponse(responseCode = "404", description = "Grupo ou Permissão não encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> desassociar(@ApiParam(value = "ID de um Grupo", example = "1") Long grupoId,
			@ApiParam(value = "ID de uma Permissão", example = "1") Long permissaoId);

	
}
