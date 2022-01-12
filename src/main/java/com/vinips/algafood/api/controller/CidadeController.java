package com.vinips.algafood.api.controller;

import java.util.List;

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

import com.vinips.algafood.domain.exception.EntidadeEmUsoException;
import com.vinips.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.vinips.algafood.domain.model.Cidade;
import com.vinips.algafood.domain.repository.CidadeRepository;
import com.vinips.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@GetMapping
	public ResponseEntity<List<Cidade>> listar(){
		
		List<Cidade> cidades = cidadeRepository.findAll();
		
		if(cidades != null && !cidades.isEmpty()) {
			return ResponseEntity.ok(cidades);
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping("/{cidadeId}")
	public Cidade buscar(@PathVariable Long cidadeId){
		// Jeito Simplificado
		return cadastroCidade.buscarOuFalhar(cidadeId);
		
		// Jeito Antigo
//		if(cidade.isPresent()) {
//			return ResponseEntity.ok(cidade.get());
//		}
//		
//		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade){
		 return cadastroCidade.salvar(cidade);
	}
	
	@PutMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.OK)
	public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade){
		
		// Jeito simplificado
		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
		
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		
		return cadastroCidade.salvar(cidadeAtual);
		
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
