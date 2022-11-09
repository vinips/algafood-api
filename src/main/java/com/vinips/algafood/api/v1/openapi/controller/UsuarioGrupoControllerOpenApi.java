package com.vinips.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.v1.model.dto.GrupoDTO;

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
@Api(tags = "Usuários") //Essa tags é o mesmo que especificamos no apiDocket da classe SpringFoxConfig
public interface UsuarioGrupoControllerOpenApi {
	
	@ApiOperation("Lista os Grupos vinculados a um Usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do Usuário inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CollectionModel<GrupoDTO> listar(@ApiParam(value = "ID de um Usuário", example = "1") Long usuarioId);

	@ApiOperation("Associa um Usuário a um Grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Usuário vinculado ao Grupo"),
		@ApiResponse(responseCode = "404", description = "Usuário ou Grupo não encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> associar(@ApiParam(value = "ID de um Usuário", example = "1") Long usuarioId,
			@ApiParam(value = "ID de um Grupo", example = "1") Long grupoId);
	
	@ApiOperation("Desassocia um Usuário de um Grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Usuário desvinculada do Grupo"),
		@ApiResponse(responseCode = "404", description = "Usuário ou Grupo não encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<Void> desassociar(@ApiParam(value = "ID de um Usuário", example = "1") Long usuarioId,
			@ApiParam(value = "ID de um Grupo", example = "1") Long grupoId);

	
}
