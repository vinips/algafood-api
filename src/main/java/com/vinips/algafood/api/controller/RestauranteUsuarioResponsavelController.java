package com.vinips.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.AlgaLinks;
import com.vinips.algafood.api.model.assembler.UsuarioDTOAssembler;
import com.vinips.algafood.api.model.dto.UsuarioDTO;
import com.vinips.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.vinips.algafood.domain.model.Restaurante;
import com.vinips.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private UsuarioDTOAssembler usuarioAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;

	@GetMapping
	public CollectionModel<UsuarioDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

		CollectionModel<UsuarioDTO> usuariosDTO =  usuarioAssembler.toCollectionModel(restaurante.getResponsaveis())
				.removeLinks()
				.add(algaLinks.linkToRestauranteUsuarioResponsavel(restauranteId))
				.add(algaLinks.linkToRestauranteUsuarioResponsavelAssociacao(restauranteId, "associar"));
		
		usuariosDTO.getContent().forEach(dto -> {
			dto.add(algaLinks.linkToRestauranteUsuarioResponsavelDesassociacao(restauranteId, dto.getId(), "desassociar"));
		});

		return usuariosDTO;
	}

	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestaurante.associarUsuarioResponsavel(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestaurante.desassociarUsuarioResponsavel(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}

}
