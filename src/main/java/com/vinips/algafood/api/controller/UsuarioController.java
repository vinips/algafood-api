package com.vinips.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.model.assembler.UsuarioDTOAssembler;
import com.vinips.algafood.api.model.disassembler.UsuarioInputDisassembler;
import com.vinips.algafood.api.model.disassembler.UsuarioSemSenhaInputDisassembler;
import com.vinips.algafood.api.model.dto.UsuarioDTO;
import com.vinips.algafood.api.model.input.SenhaInput;
import com.vinips.algafood.api.model.input.UsuarioInput;
import com.vinips.algafood.api.model.input.UsuarioSemSenhaInput;
import com.vinips.algafood.domain.model.Usuario;
import com.vinips.algafood.domain.repository.UsuarioRepository;
import com.vinips.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

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
	public ResponseEntity<List<UsuarioDTO>> listar() {
		List<Usuario> usuarios = usuarioRepository.findAll();

		if (usuarios != null && !usuarios.isEmpty()) {
			return ResponseEntity.ok(usuarioAssembler.toCollectionDTO(usuarios));
		}

		return ResponseEntity.noContent().build();

	}

	@GetMapping("/{usuarioId}")
	public UsuarioDTO buscar(@PathVariable Long usuarioId) {
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

		return usuarioAssembler.toDTO(usuario);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar(@Valid @RequestBody UsuarioInput usuarioInput) {
		Usuario usuario = usuarioDisassembler.toDomainModel(usuarioInput);

		return usuarioAssembler.toDTO(cadastroUsuario.salvar(usuario));
	}

	@PutMapping("/{usuarioId}")
	public UsuarioDTO atualizar(@PathVariable Long usuarioId,
			@Valid @RequestBody UsuarioSemSenhaInput usuarioSemSenhaInput) {
		Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);

		usuarioSemSenhaDisassembler.copyToDomainObject(usuarioSemSenhaInput, usuarioAtual);

		return usuarioAssembler.toDTO(cadastroUsuario.salvar(usuarioAtual));
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
