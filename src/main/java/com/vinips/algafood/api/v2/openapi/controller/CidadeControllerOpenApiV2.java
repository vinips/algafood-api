package com.vinips.algafood.api.v2.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.v2.model.dto.CidadeDTOV2;
import com.vinips.algafood.api.v2.model.input.CidadeInputV2;

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
@Api(tags = "Cidades") //Essa tags é o mesmo que especificamos no apiDocket da classe SpringFoxConfig
public interface CidadeControllerOpenApiV2 {

	//Essa anotação faz com que em vez de aparecer o nome do método, criado automaticamente pelo Swagger, na documentação, apareça o que determinarmos nessa anotação.
	@ApiOperation("Lista as Cidades")
	public CollectionModel<CidadeDTOV2> listar();
	
	@ApiOperation("Busca uma Cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da Cidade inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CidadeDTOV2 buscar(@ApiParam(value = "ID de uma Cidade", example = "1") Long cidadeId);
	
	@ApiOperation("Cadastra uma Cidade")
	public CidadeDTOV2 adicionar(CidadeInputV2 cidadeInput);
	
	@ApiOperation("Atualiza uma Cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cidade atualizada"),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CidadeDTOV2 atualizar(@ApiParam(value = "ID de uma Cidade", example = "1") Long cidadeId,
			CidadeInputV2 cidadeInput);
	

}
