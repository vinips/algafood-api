package com.vinips.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.model.assembler.UsuarioDTOAssembler;
import com.vinips.algafood.api.model.dto.UsuarioDTO;
import com.vinips.algafood.domain.model.Restaurante;
import com.vinips.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private UsuarioDTOAssembler usuarioAssembler;

	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

		if (restaurante.getResponsaveis() != null && !restaurante.getResponsaveis().isEmpty()) {
			return ResponseEntity.ok(usuarioAssembler.toCollectionDTO(restaurante.getResponsaveis()));
		}

		return ResponseEntity.noContent().build();

	}

	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestaurante.associarUsuarioResponsavel(restauranteId, usuarioId);
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestaurante.desassociarUsuarioResponsavel(restauranteId, usuarioId);
	}

}
