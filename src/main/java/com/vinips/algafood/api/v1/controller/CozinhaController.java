package com.vinips.algafood.api.v1.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import com.vinips.algafood.api.v1.model.assembler.CozinhaDTOAssembler;
import com.vinips.algafood.api.v1.model.disassembler.CozinhaInputDisassembler;
import com.vinips.algafood.api.v1.model.dto.CozinhaDTO;
import com.vinips.algafood.api.v1.model.input.CozinhaInput;
import com.vinips.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.vinips.algafood.domain.model.Cozinha;
import com.vinips.algafood.domain.repository.CozinhaRepository;
import com.vinips.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(path = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi{
	
	private static final Logger log = LoggerFactory.getLogger(CozinhaController.class);

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaDTOAssembler cozinhaAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	//Pageable é para a paginação.
	//Usamos o parametro size e um valor int na requisição http para determinar quantos elementos por página.
	//Usamos o parametro page e um valor int na requisição http para escolher qual página queremos.
	//Usamos o parametro page e um valor String na requisição http para ordenar por coluna.
	@GetMapping
	public PagedModel<CozinhaDTO> listar(Pageable pageable) {
		log.info("Testando Log");
		
		Page<Cozinha> cozinhasPage = this.cozinhaRepository.findAll(pageable);

		//O PagedResourcesAssembler faz com que converta uma Cozinha em uma CozinhaDTO pelo cozinhaAssembler e depois converte o Paged para PagedModel
		PagedModel<CozinhaDTO> cozinhasPagedDTO = pagedResourcesAssembler
				.toModel(cozinhasPage, cozinhaAssembler);
		
		//Antes do Hateoas
//		List<CozinhaDTO> cozinhasDTO = cozinhaAssembler.toCollectionModel(cozinhasPage.getContent());
//		
//		Page<CozinhaDTO> cozinhasDTOPage = new PageImpl<>(cozinhasDTO, pageable, cozinhasPage.getTotalElements());
		
		return cozinhasPagedDTO;
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {

		// Jeito simplificado
		return cozinhaAssembler.toModel(cadastroCozinha.buscarOuFalhar(cozinhaId));

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
		
		return cozinhaAssembler.toModel(cadastroCozinha.salvar(cozinha));
	}

	@PutMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.OK)
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @Valid @RequestBody CozinhaInput cozinhaInput) {

		// Jeito simplificado
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
		
		cozinhaDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

		//BeanUtils.copyProperties(cozinhaInput, cozinhaAtual, "id");

		return cozinhaAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));

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

	
	//Legado
//	@GetMapping("/por-nome")
//	public List<Cozinha> cozinhasPorNome(String nome) {
//		return cozinhaRepository.findListaByNomeContaining(nome);
//	}
//
//	@GetMapping("/unica-por-nome")
//	public Cozinha cozinhaPorNome(String nome) {
//		return cozinhaRepository.findUnicaByNome(nome);
//	}
//
//	@GetMapping("/existe-por-nome")
//	public boolean cozinhaExistsByNome(String nome) {
//		return cozinhaRepository.existsByNomeContains(nome);
//	}

}
