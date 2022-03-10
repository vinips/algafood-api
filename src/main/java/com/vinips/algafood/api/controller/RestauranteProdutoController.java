package com.vinips.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.model.assembler.ProdutoDTOAssembler;
import com.vinips.algafood.api.model.disassembler.ProdutoInputDisassembler;
import com.vinips.algafood.api.model.dto.ProdutoDTO;
import com.vinips.algafood.api.model.input.ProdutoInput;
import com.vinips.algafood.domain.model.Produto;
import com.vinips.algafood.domain.model.Restaurante;
import com.vinips.algafood.domain.service.CadastroProdutoService;
import com.vinips.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private ProdutoDTOAssembler produtoAssembler;

	@Autowired
	private ProdutoInputDisassembler produtoDisssembler;

	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = verifyRestaurante(restauranteId);

		if (restaurante.getProdutos() != null && !restaurante.getProdutos().isEmpty()) {
			return ResponseEntity.ok(produtoAssembler.toCollectionDTO(restaurante.getProdutos()));
		}

		return ResponseEntity.noContent().build();

	}

	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		verifyRestaurante(restauranteId);
		Produto produto = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);

		return produtoAssembler.toDTO(produto);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar(@PathVariable Long restauranteId, @Valid @RequestBody ProdutoInput produtoInput) {
		Restaurante restaurante = verifyRestaurante(restauranteId);
		Produto produto = produtoDisssembler.toDomainModel(produtoInput);
		produto.setRestaurante(restaurante);

		return produtoAssembler.toDTO(cadastroProduto.salvar(produto));
	}

	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid @RequestBody ProdutoInput produtoInput) {
		
		verifyRestaurante(restauranteId);
		Produto produto = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);
		
		produtoDisssembler.copyToDomainObject(produtoInput, produto);

		return produtoAssembler.toDTO(cadastroProduto.salvar(produto));
	}
	
	private Restaurante verifyRestaurante(Long restauranteId) {
		return cadastroRestaurante.buscarOuFalhar(restauranteId);
	}

}
