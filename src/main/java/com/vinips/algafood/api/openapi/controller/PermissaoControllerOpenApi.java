package com.vinips.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.model.dto.PermissaoDTO;

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
@Api(tags = "Permissões") //Essa tags é o mesmo que especificamos no apiDocket da classe SpringFoxConfig
public interface PermissaoControllerOpenApi {

	//Essa anotação faz com que em vez de aparecer o nome do método, criado automaticamente pelo Swagger, na documentação, apareça o que determinarmos nessa anotação.
	@ApiOperation("Lista as Permissões")
	public CollectionModel<PermissaoDTO> listar();
	
	@ApiOperation("Busca uma Permissão por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da Permissão inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Permissão não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public PermissaoDTO buscar(@ApiParam(value = "ID de uma Permissão", example = "1")  Long grupoId);

}
