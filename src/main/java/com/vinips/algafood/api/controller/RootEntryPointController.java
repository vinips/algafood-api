package com.vinips.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.AlgaLinks;
import com.vinips.algafood.api.model.dto.RootEntryPointDTO;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {
	
	@Autowired
	private AlgaLinks algaLinks;

	@GetMapping
	public RootEntryPointDTO root() {
		var rootDTO = new RootEntryPointDTO();
		
		rootDTO.add(algaLinks.linkToCozinhas("cozinhas"));
		rootDTO.add(algaLinks.linkToPedidos("pedidos"));
		rootDTO.add(algaLinks.linkToRestaurantes("restaurantes"));
		rootDTO.add(algaLinks.linkToGrupos("grupos"));
		rootDTO.add(algaLinks.linkToUsuarios("usuarios"));
		rootDTO.add(algaLinks.linkToPermissoes("permissoes"));
		rootDTO.add(algaLinks.linkToFormasPagamento("formas-pagamento"));
		rootDTO.add(algaLinks.linkToEstados("estados"));
		rootDTO.add(algaLinks.linkToCidades("cidades"));
		
		return rootDTO;
	}
	
}
