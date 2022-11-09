package com.vinips.algafood.api.v1.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.PathVariable;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.v1.model.dto.FotoProdutoDTO;
import com.vinips.algafood.api.v1.model.input.FotoProdutoInput;

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
public interface RestauranteFotoProdutoControllerOpenApi {
	
	@ApiOperation("Atualiza um Produto com uma nova Foto")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Foto do Produto atualizada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "400", description = "ID do Restaurante/Produto inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante/Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public FotoProdutoDTO atualizarFoto(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId,
			@ApiParam(value = "ID de um Restaurante", example = "1") Long produtoId, 
			FotoProdutoInput fotoProdutoInput) throws IOException;
	
	@ApiOperation(value = "Busca a Foto de um Produto vinculado a um Restaurante", produces = "application/json, image/jpeg, image/png")
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "OK", content = @Content(schema = @Schema(implementation = FotoProdutoDTO.class), mediaType = "application/json")),
		@ApiResponse(responseCode = "200",description = "OK", content = @Content(mediaType = "image/png")),
		@ApiResponse(responseCode = "200",description = "OK", content = @Content(mediaType = "image/jpeg")),
		@ApiResponse(responseCode = "400", description = "ID do Restaurante/Produto inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante/Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<?> servirFoto(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId,
			@ApiParam(value = "ID de um Produto", example = "1") Long produtoId,
			@ApiParam(hidden = true, required = false) String acceptHeader) throws HttpMediaTypeNotAcceptableException;
	
	@ApiOperation(value = "Busca a Foto de um Produto vinculado a um Restaurante", hidden = true)
	public FotoProdutoDTO buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId);
	
	@ApiOperation("Remove a Foto de um Produto vinculado a um Restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Foto do Produto removida com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "400", description = "ID do Restaurante/Produto inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante/Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void removerFotoProduto(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId,
			@ApiParam(value = "ID de um Produto", example = "1") Long produtoId);
	
}
