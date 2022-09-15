package com.vinips.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.model.dto.ProdutoDTO;
import com.vinips.algafood.api.model.input.ProdutoInput;

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
@Api(tags = "Produtos") //Essa tags é o mesmo que especificamos no apiDocket da classe SpringFoxConfig
public interface RestauranteProdutoControllerOpenApi {

	//Essa anotação faz com que em vez de aparecer o nome do método, criado automaticamente pelo Swagger, na documentação, apareça o que determinarmos nessa anotação.
	@ApiOperation("Lista os Produtos vinculados a um Restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do Restaurante inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public CollectionModel<ProdutoDTO> listar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId,
			@ApiParam(value = "Indica se deve ou não incluir produtos inativos no resultado da listagem", example = "false", defaultValue = "false") Boolean incluirInativos);
	
	@ApiOperation("Busca um Produto vinculado a um Restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do Restaurante/Produto inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante/Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ProdutoDTO buscar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId,
			@ApiParam(value = "ID de um Produto", example = "1") Long produtoId);
	
	@ApiOperation("Cadastra um Produto em um Restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do Restaurante inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ProdutoDTO adicionar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId,
			ProdutoInput produtoInput);
	
	@ApiOperation("Atualiza um Produto de um Restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do Restaurante/Produto inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante/Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ProdutoDTO atualizar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId,
			@ApiParam(value = "ID de um Restaurante", example = "1") Long produtoId, ProdutoInput produtoInput);

}
