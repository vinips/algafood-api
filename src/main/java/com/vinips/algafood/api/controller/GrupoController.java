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

import com.vinips.algafood.api.model.assembler.GrupoDTOAssembler;
import com.vinips.algafood.api.model.disassembler.GrupoInputDisassembler;
import com.vinips.algafood.api.model.dto.GrupoDTO;
import com.vinips.algafood.api.model.input.GrupoInput;
import com.vinips.algafood.domain.model.Grupo;
import com.vinips.algafood.domain.repository.GrupoRepository;
import com.vinips.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private GrupoDTOAssembler grupoAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoDisassembler;
	
	@GetMapping
	public ResponseEntity<List<GrupoDTO>> listar() {
		List<Grupo> grupos = grupoRepository.findAll();

		if (grupos != null && !grupos.isEmpty()) {
			return ResponseEntity.ok(grupoAssembler.toCollectionDTO(grupos));
		}

		return ResponseEntity.noContent().build();

	}

	@GetMapping("/{grupoId}")
	public GrupoDTO buscar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

		return grupoAssembler.toDTO(grupo);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar(@Valid @RequestBody GrupoInput grupoInput) {
		Grupo grupo = grupoDisassembler.toDomainModel(grupoInput);

		return grupoAssembler.toDTO(cadastroGrupo.salvar(grupo));
	}

	@PutMapping("/{grupoId}")
	public GrupoDTO atualizar(@PathVariable Long grupoId,
			@Valid @RequestBody GrupoInput grupoInput) {
		Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);

		grupoDisassembler.copyToDomainObject(grupoInput, grupoAtual);

		return grupoAssembler.toDTO(cadastroGrupo.salvar(grupoAtual));
	}

	@DeleteMapping("/{grupoId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {
		cadastroGrupo.excluir(grupoId);
	}
	

}