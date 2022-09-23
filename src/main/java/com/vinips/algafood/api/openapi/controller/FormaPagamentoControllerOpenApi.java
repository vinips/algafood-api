package com.vinips.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.model.dto.FormaPagamentoDTO;
import com.vinips.algafood.api.model.input.FormaPagamentoInput;
import com.vinips.algafood.api.openapi.dto.FormasPagamentoDTOOpenApi;

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
@Api(tags = "Formas de Pagamento") //Essa tags é o mesmo que especificamos no apiDocket da classe SpringFoxConfig
public interface FormaPagamentoControllerOpenApi {
	
	//Essa anotação faz com que em vez de aparecer o nome do método, criado automaticamente pelo Swagger, na documentação, apareça o que determinarmos nessa anotação.
	//Precisa do response aqui pois estamos retornando um ResponseEntity<CollectionModel<FormaPagamentoDTO>>. Se não tivesse o ResponseEntity então não precisava do response. A principio é um bug
	@ApiOperation(value = "Lista as Formas de Pagamento")
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = FormasPagamentoDTOOpenApi.class) })
	public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request);
	
	@ApiOperation("Busca uma  Forma de Pagamento por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da Forma de Pagamento inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Forma de Pagamento não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public ResponseEntity<FormaPagamentoDTO>  buscar(@ApiParam(value = "ID de uma Forma de Pagamento", example = "1")  Long formaPagamentoId);
	
	@ApiOperation("Cadastra uma Forma de Pagamento")
	public FormaPagamentoDTO adicionar(FormaPagamentoInput formaPagamentoInput);
	
	@ApiOperation("Atualiza uma Forma de Pagamento por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Forma de Pagamento atualizada"),
		@ApiResponse(responseCode = "404", description = "Forma de Pagamento não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public FormaPagamentoDTO atualizar(@ApiParam(value = "ID de uma Forma de Pagamento", example = "1") Long formaPagamentoId,
			FormaPagamentoInput formaPagamentoInput);
	
	@ApiOperation("Exclui uma Forma de Pagamento por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Forma de Pagamento excluída"),
		@ApiResponse(responseCode = "404", description = "Forma de Pagamento não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void remover(@ApiParam(value = "ID de uma Forma de Pagamento", example = "1") Long formaPagamentoId);

}
