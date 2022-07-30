package com.vinips.algafood.api.controller;

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

import com.vinips.algafood.api.model.assembler.EstadoDTOAssembler;
import com.vinips.algafood.api.model.disassembler.EstadoInputDisassembler;
import com.vinips.algafood.api.model.dto.EstadoDTO;
import com.vinips.algafood.api.model.input.EstadoInput;
import com.vinips.algafood.api.openapi.controller.EstadoControllerOpenApi;
import com.vinips.algafood.domain.model.Estado;
import com.vinips.algafood.domain.repository.EstadoRepository;
import com.vinips.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoDTOAssembler estadoAssembler;
	
	@Autowired
	private EstadoInputDisassembler estadoDisassembler;
	
	@GetMapping
	public CollectionModel<EstadoDTO> listar() {

		List<Estado> estados = estadoRepository.findAll();

		return estadoAssembler.toCollectionModel(estados);
	}
	
	@GetMapping("/{estadoId}")
	public EstadoDTO buscar(@PathVariable Long estadoId) {
		//Jeito Simplificado
		return estadoAssembler.toModel(cadastroEstado.buscarOuFalhar(estadoId));

		// Jeito Antigo
//		if (estado.isPresent()) {
//			return ResponseEntity.ok(estado.get());
//		}
//
//		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@Valid @RequestBody EstadoInput estadoInput) {
		Estado estado = estadoDisassembler.toDomainModel(estadoInput);
		
		return estadoAssembler.toModel(cadastroEstado.salvar(estado));
	}

	@PutMapping("/{estadoId}")
	public EstadoDTO atualizar(@PathVariable Long estadoId, @Valid @RequestBody EstadoInput estadoInput) {

		// Jeito simplificado
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
		
		estadoDisassembler.copyToDomainObject(estadoInput, estadoAtual);
		
		return estadoAssembler.toModel(cadastroEstado.salvar(estadoAtual));
		
		// Jeito Antigo
//		if(estadoAtual.isPresent()) {
//			BeanUtils.copyProperties(estado, estadoAtual.get(), "id");
//			Estado estadoSalvo = cadastroEstado.salvar(estadoAtual.get());
//			return ResponseEntity.ok(estadoSalvo);
//		}
//		
//		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId){
		cadastroEstado.excluir(estadoId);
	}

	
}
