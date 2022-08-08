package com.vinips.algafood.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.model.dto.CozinhaDTO;
import com.vinips.algafood.api.model.input.CozinhaInput;

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
@Api(tags = "Cozinhas") //Essa tags é o mesmo que especificamos no apiDocket da classe SpringFoxConfig
public interface CozinhaControllerOpenApi {

	//Essa anotação faz com que em vez de aparecer o nome do método, criado automaticamente pelo Swagger, na documentação, apareça o que determinarmos nessa anotação.
	@ApiOperation("Lista as Cozinhas")
	public PagedModel<CozinhaDTO> listar(Pageable pageable);
	
	@ApiOperation("Busca uma Cozinha por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da Cozinha inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CozinhaDTO buscar(@ApiParam(value = "ID de um Cozinha", example = "1") Long cozinhaId);
	
	@ApiOperation("Cadastra uma Cozinha")
	public CozinhaDTO adicionar(CozinhaInput cozinhaInput);
	
	@ApiOperation("Atualiza uma Cozinha por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CozinhaDTO atualizar(@ApiParam(value = "ID de uma Cozinha", example = "1") Long cozinhaId,
			CozinhaInput cozinhaInput);
	
	@ApiOperation("Exclui uma Cozinha por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Cozinha excluída"),
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void remover(@ApiParam(value = "ID de um Cozinha", example = "1") Long cozinhaId);
	
}
