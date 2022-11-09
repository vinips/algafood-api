package com.vinips.algafood.api.v2.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.ResourceUriHelper;
import com.vinips.algafood.api.v2.model.assembler.CidadeDTOAssemblerV2;
import com.vinips.algafood.api.v2.model.disassembler.CidadeInputDisassemblerV2;
import com.vinips.algafood.api.v2.model.dto.CidadeDTOV2;
import com.vinips.algafood.api.v2.model.input.CidadeInputV2;
import com.vinips.algafood.api.v2.openapi.controller.CidadeControllerOpenApiV2;
import com.vinips.algafood.domain.exception.EstadoNaoEncontradoException;
import com.vinips.algafood.domain.exception.NegocioException;
import com.vinips.algafood.domain.model.Cidade;
import com.vinips.algafood.domain.repository.CidadeRepository;
import com.vinips.algafood.domain.service.CadastroCidadeService;


@RestController
@RequestMapping(path = "/v2/cidades",  produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerOpenApiV2 {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeDTOAssemblerV2 cidadeAssembler;
	
	@Autowired
	private CidadeInputDisassemblerV2 cidadeDisassembler;
	
	@Deprecated
	//Aqui usamos CollectionModel pois dentro dele tem uma Lista de Representation Model(DTO) que é o que iremos usar para os links do HATEOAS também.
	@GetMapping
	public CollectionModel<CidadeDTOV2> listar(){
		
		List<Cidade> cidades = cidadeRepository.findAll();
		
		return cidadeAssembler.toCollectionModel(cidades);
		
		//cidadesCollectionDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
	}
	
	@Deprecated
	@GetMapping("/{cidadeId}")
	public CidadeDTOV2 buscar(@PathVariable Long cidadeId) {
		// Jeito Simplificado
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		System.out.println("oi");
		return cidadeAssembler.toModel(cidade);
		
	}
	
	@Deprecated
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTOV2 adicionar(@Valid @RequestBody CidadeInputV2 cidadeInput) {
		 try {
			 
			 Cidade cidade = cidadeDisassembler.toDomainModel(cidadeInput);
			 
			 CidadeDTOV2 cidadeDTO = cidadeAssembler.toModel(cadastroCidade.salvar(cidade));

			 ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getIdCidade());
				
			 return cidadeDTO;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Deprecated
	@PutMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.OK)
	public CidadeDTOV2 atualizar(@PathVariable Long cidadeId, @Valid @RequestBody CidadeInputV2 cidadeInput) {
		try {
			// Jeito simplificado
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

			cidadeDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			return cidadeAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}
	
}
