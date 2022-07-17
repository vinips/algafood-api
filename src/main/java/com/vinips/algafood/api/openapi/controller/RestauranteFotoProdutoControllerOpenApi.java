package com.vinips.algafood.api.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.model.dto.FotoProdutoDTO;
import com.vinips.algafood.api.model.input.FotoProdutoInput;

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
		@ApiResponse(responseCode = "400", description = "ID do Restaurante/Produto inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante/Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public FotoProdutoDTO buscarFoto(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId,
			@ApiParam(value = "ID de um Produto", example = "1") Long produtoId);
	
	@ApiOperation(value = "Busca a Foto de um Produto vinculado a um Restaurante", hidden = true)
	public ResponseEntity<?> servirFoto(Long restauranteId, Long produtoId, String acceptHeader) throws HttpMediaTypeNotAcceptableException;
	
	@ApiOperation("Remove a Foto de um Produto vinculado a um Restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Foto do Produto removida com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "400", description = "ID do Restaurante/Produto inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante/Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void removerFotoProduto(@ApiParam(value = "ID de um Restaurante", example = "1") Long restauranteId,
			@ApiParam(value = "ID de um Produto", example = "1") Long produtoId);
	
}
