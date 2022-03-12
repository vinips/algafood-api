package com.vinips.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.model.assembler.PermissaoDTOAssembler;
import com.vinips.algafood.api.model.dto.PermissaoDTO;
import com.vinips.algafood.domain.model.Grupo;
import com.vinips.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private PermissaoDTOAssembler permissaoAssembler;
	
	@GetMapping
	public ResponseEntity<List<PermissaoDTO>> listar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

		if (grupo.getPermissoes() != null && !grupo.getPermissoes().isEmpty()) {
			return ResponseEntity.ok(permissaoAssembler.toCollectionDTO(grupo.getPermissoes()));
		}

		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.associarPermissao(grupoId, permissaoId);
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
	}
	


}
