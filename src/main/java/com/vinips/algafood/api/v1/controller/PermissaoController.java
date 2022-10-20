package com.vinips.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.v1.model.assembler.PermissaoDTOAssembler;
import com.vinips.algafood.api.v1.model.dto.PermissaoDTO;
import com.vinips.algafood.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.vinips.algafood.domain.model.Permissao;
import com.vinips.algafood.domain.repository.PermissaoRepository;
import com.vinips.algafood.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping(path = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi{
	
	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private CadastroPermissaoService cadastroPermissao;
	
	@Autowired
	private PermissaoDTOAssembler permissaoAssembler;
	
	@GetMapping
	public CollectionModel<PermissaoDTO> listar(){
		List<Permissao> permissoes = permissaoRepository.findAll();
		
		return permissaoAssembler.toCollectionModel(permissoes);
	}
	
	@GetMapping("/{permissaoId}")
	public PermissaoDTO buscar(@PathVariable Long permissaoId) {
		Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);
		
		return permissaoAssembler.toModel(permissao);
	}
	

}
