package com.vinips.algafood.api.openapi.controller;

import java.util.List;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.model.dto.RestauranteDTO;
import com.vinips.algafood.api.model.input.RestauranteInput;
import com.vinips.algafood.api.openapi.dto.RestauranteBasicoDTOOpenApi;

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
@Api(tags = "Restaurantes") //Essa tags é o mesmo que especificamos no apiDocket da classe SpringFoxConfig
public interface RestauranteControllerOpenApi {
	
	//Essa anotação faz com que em vez de aparecer o nome do método, criado automaticamente pelo Swagger, na documentação, apareça o que determinarmos nessa anotação.
	//o response diz que na documentação o SpringFox deve usar na resposta a classe dita ali, e não o retorno declarado no método. Só fazemos assim pois estamos utilizando JsonView nesse método.
	//Vou deixar a fim de documentação, porém o SpringFox 3.0 já faz certinho e não precisa desse response.
	@ApiOperation(value = "Lista Restaurantes", response = RestauranteBasicoDTOOpenApi.class)
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção de Restaurantes", name = "projecao", allowableValues = "apenas-nome", paramType = "query", type = "string")
	})
	public List<RestauranteDTO> listar();
	
	@ApiOperation(value = "Lista Restaurantes", hidden = true)
	public List<RestauranteDTO> listarApenasNome();
	
	@ApiOperation("Busca um Restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do Restaurante inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public RestauranteDTO buscar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId);
	
	@ApiOperation("Cadastra um Restaurante")
	public RestauranteDTO adicionar( RestauranteInput restauranteInput);
	
	@ApiOperation("Atualiza um Restaurante pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public RestauranteDTO atualizar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId, RestauranteInput restauranteInput);
	
	@ApiOperation("Exclui um Restaurante pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Restaurante excluído"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void remover(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId);
	
	@ApiOperation("Ativa um Restaurante pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Restaurante ativado"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void ativar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId);
	
	@ApiOperation("Ativa multiplos Restaurantes pelos IDs")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Restaurantes ativados"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void ativarMultiplos(@ApiParam(value = "Lista de um ou mais IDs de um Restaurante", example = "[1,2,3,4]") List<Long> restauranteIds);
	
	@ApiOperation("Inativa um Restaurante pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Restaurantes inativados"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void inativar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId);
	
	@ApiOperation("Inativa multiplos Restaurantes pelos IDs")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Restaurantes inativados"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void inativarMultiplos(@ApiParam(value = "Lista de um ou mais IDs de um Restaurante", example = "[1,2,3,4]") List<Long> restauranteIds);
	
	@ApiOperation("Abre um Restaurante pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Restaurantes aberto"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void abrir(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId);
	
	@ApiOperation("Fecha um Restaurante pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Restaurantes fechado"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void fechar(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId);

	
}
