package com.vinips.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.ResourceUriHelper;
import com.vinips.algafood.api.v1.model.assembler.CidadeDTOAssembler;
import com.vinips.algafood.api.v1.model.disassembler.CidadeInputDisassembler;
import com.vinips.algafood.api.v1.model.dto.CidadeDTO;
import com.vinips.algafood.api.v1.model.input.CidadeInput;
import com.vinips.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.vinips.algafood.core.web.AlgaMediaTypes;
import com.vinips.algafood.domain.exception.EstadoNaoEncontradoException;
import com.vinips.algafood.domain.exception.NegocioException;
import com.vinips.algafood.domain.model.Cidade;
import com.vinips.algafood.domain.repository.CidadeRepository;
import com.vinips.algafood.domain.service.CadastroCidadeService;


@RestController
//MediaType Customizado. Qualquer problema olhar na aula 20.10
@RequestMapping(path = "/cidades", produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi{
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeDTOAssembler cidadeAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeDisassembler;
	
	//Aqui usamos CollectionModel pois dentro dele tem uma Lista de Representation Model(DTO) que é o que iremos usar para os links do HATEOAS também.
	@GetMapping
	public CollectionModel<CidadeDTO> listar(){
		
		List<Cidade> cidades = cidadeRepository.findAll();
		
		return cidadeAssembler.toCollectionModel(cidades);
		
		//cidadesCollectionDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
	}
	
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {
		// Jeito Simplificado
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		System.out.println("oi");
		return cidadeAssembler.toModel(cidade);
		
		
		//Link por Método (FOI TRANSFERIDO PRO ASSEMBLER
//		cidadeDTO.add(WebMvcLinkBuilder
//				.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscar(cidadeDTO.getId())).withSelfRel());
//
//		cidadeDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).listar())
//				.withRel("cidades"));
//
//		cidadeDTO.getEstado()
//				.add(WebMvcLinkBuilder.linkTo(
//						WebMvcLinkBuilder.methodOn(EstadoController.class).buscar(cidadeDTO.getEstado().getId()))
//						.withSelfRel());
		
		//Link por Controlador
//		cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
//				.slash(cidadeDTO.getId()).withSelfRel());
		
//		cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
//				.withRel("cidades"));
		
//		cidadeDTO.getEstado().add(WebMvcLinkBuilder.linkTo(EstadoController.class)
//		.slash(cidadeDTO.getEstado().getId()).withSelfRel());
		
		//Link Manual
//		cidadeDTO.add(new Link("http://localhost:8080/cidades/3", IanaLinkRelations.SELF));
//		cidadeDTO.add(new Link("http://localhost:8080/cidades", IanaLinkRelations.COLLECTION));
//		cidadeDTO.add(new Link("http://localhost:8080/cidades/3"));
//		cidadeDTO.add(new Link("http://localhost:8080/cidades", "cidades"));
//		cidadeDTO.getEstado().add(new Link("http://localhost:8080/estados/3"));
		
		// Jeito Antigo
//		if(cidade.isPresent()) {
//			return ResponseEntity.ok(cidade.get());
//		}
//		
//		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@Valid @RequestBody CidadeInput cidadeInput) {
		 try {
			 
			 Cidade cidade = cidadeDisassembler.toDomainModel(cidadeInput);
			 
			 CidadeDTO cidadeDTO = cidadeAssembler.toModel(cadastroCidade.salvar(cidade));

			 ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());
				
			 return cidadeDTO;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.OK)
	public CidadeDTO atualizar(@PathVariable Long cidadeId,
			@Valid @RequestBody CidadeInput cidadeInput) {
		try {
			// Jeito simplificado
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
			
			cidadeDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
			
			return cidadeAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
		// Jeito Antigo
//		if(cidadeAtual.isPresent()) {
//			BeanUtils.copyProperties(cidade, cidadeAtual.get(), "id");
//			Cidade cidadeSalva= cadastroCidade.salvar(cidadeAtual.get());
//			
//			return ResponseEntity.ok(cidadeSalva);
//		}
//		
//		return ResponseEntity.notFound().build();
//		
//	} catch (EntidadeNaoEncontradaException e) {
//		return ResponseEntity.badRequest().body(e.getMessage());
//	}
		
	}
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId){
		cadastroCidade.excluir(cidadeId);
	}
	

}
