package com.vinips.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.v1.model.dto.GrupoDTO;
import com.vinips.algafood.api.v1.model.input.GrupoInput;

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
public interface GrupoControllerOpenApi {

	//Essa anotação faz com que em vez de aparecer o nome do método, criado automaticamente pelo Swagger, na documentação, apareça o que determinarmos nessa anotação.
	@ApiOperation("Lista os Grupos")
	public CollectionModel<GrupoDTO> listar();
	
	@ApiOperation("Busca um Grupo por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do Grupo inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public GrupoDTO buscar(@ApiParam(value = "ID de um Grupo", example = "1")  Long grupoId);
	
	@ApiOperation("Cadastra um Grupo")
	public GrupoDTO adicionar(GrupoInput grupoInput);
	
	@ApiOperation("Atualiza um Grupo por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Grupo atualizado"),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public GrupoDTO atualizar(@ApiParam(value = "ID de um Grupo", example = "1") Long grupoId,
			GrupoInput grupoInput);
	
	@ApiOperation("Exclui um Grupo por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Grupo excluído"),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void remover(@ApiParam(value = "ID de um Grupo", example = "1") Long grupoId);

}
