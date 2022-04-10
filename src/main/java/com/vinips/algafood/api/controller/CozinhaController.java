package com.vinips.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import com.vinips.algafood.api.model.assembler.CozinhaDTOAssembler;
import com.vinips.algafood.api.model.disassembler.CozinhaInputDisassembler;
import com.vinips.algafood.api.model.dto.CozinhaDTO;
import com.vinips.algafood.api.model.input.CozinhaInput;
import com.vinips.algafood.domain.model.Cozinha;
import com.vinips.algafood.domain.repository.CozinhaRepository;
import com.vinips.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaDTOAssembler cozinhaAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaDisassembler;

	//Pageable é para a paginação.
	//Usamos o parametro size e um valor int na requisição http para determinar quantos elementos por página.
	//Usamos o parametro page e um valor int na requisição http para escolher qual página queremos.
	//Usamos o parametro page e um valor String na requisição http para ordenar por coluna.
	@GetMapping
	public Page<CozinhaDTO> listar(Pageable pageable) {
		Page<Cozinha> cozinhasPage = this.cozinhaRepository.findAll(pageable);

		List<CozinhaDTO> cozinhasDTO = cozinhaAssembler.toCollectionDTO(cozinhasPage.getContent());
		
		Page<CozinhaDTO> cozinhasDTOPage = new PageImpl<>(cozinhasDTO, pageable, cozinhasPage.getTotalElements());
		
		return cozinhasDTOPage;
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {

		// Jeito simplificado
		return cozinhaAssembler.toDTO(cadastroCozinha.buscarOuFalhar(cozinhaId));

		// Jeito antigo
//		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
//		
//		if (cozinha.isPresent()) {
//			return ResponseEntity.ok(cozinha.get());
//		}
//		
//		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@Valid @RequestBody CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaDisassembler.toDomainModel(cozinhaInput);
		
		return cozinhaAssembler.toDTO(cadastroCozinha.salvar(cozinha));
	}

	@PutMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.OK)
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @Valid @RequestBody CozinhaInput cozinhaInput) {

		// Jeito simplificado
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
		
		cozinhaDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

		//BeanUtils.copyProperties(cozinhaInput, cozinhaAtual, "id");

		return cozinhaAssembler.toDTO(cadastroCozinha.salvar(cozinhaAtual));

		// Jeito Antigo
//		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);
//
//		
//		//Copia uma entidade para outra
//		if (cozinhaAtual.isPresent()) {
//			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
//			Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAtual.get());
//			
//			return ResponseEntity.ok(cozinhaSalva);
//		}
//		
//		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}

	@GetMapping("/por-nome")
	public List<Cozinha> cozinhasPorNome(String nome) {
		return cozinhaRepository.findListaByNomeContaining(nome);
	}

	@GetMapping("/unica-por-nome")
	public Cozinha cozinhaPorNome(String nome) {
		return cozinhaRepository.findUnicaByNome(nome);
	}

	@GetMapping("/existe-por-nome")
	public boolean cozinhaExistsByNome(String nome) {
		return cozinhaRepository.existsByNomeContains(nome);
	}

}
