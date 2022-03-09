package com.vinips.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.model.assembler.ProdutoDTOAssembler;
import com.vinips.algafood.api.model.dto.ProdutoDTO;
import com.vinips.algafood.domain.model.Restaurante;
import com.vinips.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private ProdutoDTOAssembler produtoAssembler;

	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

		if (restaurante.getProdutos() != null && !restaurante.getProdutos().isEmpty()) {
			return ResponseEntity.ok(produtoAssembler.toCollectionDTO(restaurante.getProdutos()));
		}

		return ResponseEntity.noContent().build();

	}
	
	
	//TODO
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		return null;

	}

}
