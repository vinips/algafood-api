package com.vinips.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.vinips.algafood.api.model.assembler.CidadeDTOAssembler;
import com.vinips.algafood.api.model.disassembler.CidadeInputDisassembler;
import com.vinips.algafood.api.model.dto.CidadeDTO;
import com.vinips.algafood.api.model.input.CidadeInput;
import com.vinips.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.vinips.algafood.domain.exception.EstadoNaoEncontradoException;
import com.vinips.algafood.domain.exception.NegocioException;
import com.vinips.algafood.domain.model.Cidade;
import com.vinips.algafood.domain.repository.CidadeRepository;
import com.vinips.algafood.domain.service.CadastroCidadeService;


@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi{
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeDTOAssembler cidadeAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeDisassembler;
	
	@GetMapping
	public ResponseEntity<List<CidadeDTO>> listar(){
		
		List<Cidade> cidades = cidadeRepository.findAll();
		
		if(cidades != null && !cidades.isEmpty()) {
			return ResponseEntity.ok(cidadeAssembler.toCollectionDTO(cidades));
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	//Essa anotação faz com que em vez de aparecer o nome do método na documentação  feita pelo Swagger, apareça o que determinarmos nessa anotação.
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId){
		// Jeito Simplificado
		return cidadeAssembler.toDTO(cadastroCidade.buscarOuFalhar(cidadeId));
		
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
			 
			 return cidadeAssembler.toDTO(cadastroCidade.salvar(cidade));
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
			
			return cidadeAssembler.toDTO(cadastroCidade.salvar(cidadeAtual));
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
