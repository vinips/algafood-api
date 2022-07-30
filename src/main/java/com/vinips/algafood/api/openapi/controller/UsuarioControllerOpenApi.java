package com.vinips.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.model.dto.UsuarioDTO;
import com.vinips.algafood.api.model.input.SenhaInput;
import com.vinips.algafood.api.model.input.UsuarioInput;
import com.vinips.algafood.api.model.input.UsuarioSemSenhaInput;

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
@Api(tags = "Usuários") //Essa tags é o mesmo que especificamos no apiDocket da classe SpringFoxConfig
public interface UsuarioControllerOpenApi {

	//Essa anotação faz com que em vez de aparecer o nome do método, criado automaticamente pelo Swagger, na documentação, apareça o que determinarmos nessa anotação.
	@ApiOperation("Lista os Usuários")
	public CollectionModel<UsuarioDTO> listar();
	
	@ApiOperation("Busca um Usuário por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do Usuário inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public UsuarioDTO buscar(@ApiParam(value = "ID de um Usuário", example = "1") Long usuarioId);
	
	@ApiOperation("Cadastra um Usuário")
	public UsuarioDTO adicionar(UsuarioInput usuarioInput);
	
	@ApiOperation("Atualiza um Usuário por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Usuário atualizado"),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public UsuarioDTO atualizar(@ApiParam(value = "ID de um Usuário", example = "1") Long usuarioId,
			UsuarioSemSenhaInput usuarioSemSenhaInput);
	
	@ApiOperation("Exclui um Usuário por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Usuário excluído"),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void remover(@ApiParam(value = "ID de um Usuário", example = "1") Long usuarioId);
	
	@ApiOperation("Atualiza a Senha de um Usuário por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Senha atualizada"),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
	})
	public void atualizarSenha(@ApiParam(value = "ID de um Usuário", example = "1") Long usuarioId,
			SenhaInput senhaInput);

}
