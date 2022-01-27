package com.vinips.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

	@GetMapping
	public ResponseEntity<List<Cozinha>> listar() {

		List<Cozinha> cozinhas = this.cozinhaRepository.findAll();

		if (cozinhas != null && !cozinhas.isEmpty()) {
			return ResponseEntity.ok(cozinhas);
		}

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable Long cozinhaId) {

		// Jeito simplificado
		return cadastroCozinha.buscarOuFalhar(cozinhaId);

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
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.OK)
	public Cozinha atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {

		// Jeito simplificado
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);

		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

		return cadastroCozinha.salvar(cozinhaAtual);

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
