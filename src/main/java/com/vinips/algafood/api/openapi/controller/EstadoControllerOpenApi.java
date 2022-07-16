package com.vinips.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.model.dto.EstadoDTO;
import com.vinips.algafood.api.model.input.EstadoInput;

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
@Api(tags = "Estados") //Essa tags é o mesmo que especificamos no apiDocket da classe SpringFoxConfig
public interface EstadoControllerOpenApi {

	//Essa anotação faz com que em vez de aparecer o nome do método, criado automaticamente pelo Swagger, na documentação, apareça o que determinarmos nessa anotação.
	@ApiOperation("Lista as Estados")
	public ResponseEntity<List<EstadoDTO>> listar();
	
	@ApiOperation("Busca um Estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do Estado inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public EstadoDTO buscar(@ApiParam(value = "ID de um Estado", example = "1") Long estadoId);
	
	@ApiOperation("Cadastra um Estado")
	public EstadoDTO adicionar(EstadoInput estadoInput);
	
	@ApiOperation("Atualiza um Estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Estado atualizado"),
		@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public EstadoDTO atualizar(@ApiParam(value = "ID de um Estado", example = "1") Long estadoId,
			EstadoInput estadoInput);
	
	@ApiOperation("Exclui umaEstado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Estado excluído"),
		@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void remover(@ApiParam(value = "ID de um Estado", example = "1") Long estadoId);

}
