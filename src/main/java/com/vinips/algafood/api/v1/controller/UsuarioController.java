package com.vinips.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.v1.model.assembler.UsuarioDTOAssembler;
import com.vinips.algafood.api.v1.model.disassembler.UsuarioInputDisassembler;
import com.vinips.algafood.api.v1.model.disassembler.UsuarioSemSenhaInputDisassembler;
import com.vinips.algafood.api.v1.model.dto.UsuarioDTO;
import com.vinips.algafood.api.v1.model.input.SenhaInput;
import com.vinips.algafood.api.v1.model.input.UsuarioInput;
import com.vinips.algafood.api.v1.model.input.UsuarioSemSenhaInput;
import com.vinips.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.vinips.algafood.domain.model.Usuario;
import com.vinips.algafood.domain.repository.UsuarioRepository;
import com.vinips.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(path = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private UsuarioDTOAssembler usuarioAssembler;

	@Autowired
	private UsuarioInputDisassembler usuarioDisassembler;
	
	@Autowired
	private UsuarioSemSenhaInputDisassembler usuarioSemSenhaDisassembler;

	@GetMapping
	public CollectionModel<UsuarioDTO> listar() {
		List<Usuario> usuarios = usuarioRepository.findAll();

		return usuarioAssembler.toCollectionModel(usuarios);
	}

	@GetMapping("/{usuarioId}")
	public UsuarioDTO buscar(@PathVariable Long usuarioId) {
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

		return usuarioAssembler.toModel(usuario);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar(@Valid @RequestBody UsuarioInput usuarioInput) {
		Usuario usuario = usuarioDisassembler.toDomainModel(usuarioInput);

		return usuarioAssembler.toModel(cadastroUsuario.salvar(usuario));
	}

	@PutMapping("/{usuarioId}")
	public UsuarioDTO atualizar(@PathVariable Long usuarioId,
			@Valid @RequestBody UsuarioSemSenhaInput usuarioSemSenhaInput) {
		Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);

		usuarioSemSenhaDisassembler.copyToDomainObject(usuarioSemSenhaInput, usuarioAtual);

		return usuarioAssembler.toModel(cadastroUsuario.salvar(usuarioAtual));
	}

	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long usuarioId) {
		cadastroUsuario.excluir(usuarioId);
	}
	
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void atualizarSenha(@PathVariable Long usuarioId,
			@Valid @RequestBody SenhaInput senhaInput) {
		cadastroUsuario.alterarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha() );
	}
	
}
