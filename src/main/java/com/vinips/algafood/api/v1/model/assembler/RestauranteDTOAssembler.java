package com.vinips.algafood.api.v1.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.v1.AlgaLinks;
import com.vinips.algafood.api.v1.controller.RestauranteController;
import com.vinips.algafood.api.v1.model.dto.RestauranteDTO;
import com.vinips.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO>{

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteDTOAssembler() {
		super(RestauranteController.class, RestauranteDTO.class);
	}
	
	public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
		
		modelMapper.map(restaurante, restauranteDTO);
		
		restauranteDTO.add(algaLinks.linkToRestaurantes("restaurantes"));
		
		if (restauranteDTO.isAtivo()) {
			restauranteDTO.add(algaLinks.linkToRestauranteInativacao(restauranteDTO.getId(), "inativar"));
		} else {
			restauranteDTO.add(algaLinks.linkToRestauranteAtivacao(restauranteDTO.getId(), "ativar"));
		}
		
		if (restauranteDTO.isAberto()) {
			restauranteDTO.add(algaLinks.linkToRestauranteFechamento(restauranteDTO.getId(), "fechar"));
		} else {
			restauranteDTO.add(algaLinks.linkToRestauranteAbertura(restauranteDTO.getId(), "abrir"));
		}
		
		restauranteDTO.add(algaLinks.linkToRestauranteFormasPagamento(restauranteDTO.getId(), "formas-pagamento"));
		
		restauranteDTO.add(algaLinks.linkToRestauranteUsuarioResponsavel(restauranteDTO.getId(), "responsaveis"));
		
		restauranteDTO.getCozinha().add(algaLinks.linkToCozinha(restauranteDTO.getCozinha().getId()));
		
		if (restauranteDTO.getEndereco() != null && restauranteDTO.getEndereco().getCidade() != null) {
			restauranteDTO.getEndereco().getCidade().add(algaLinks.linkToCidade(restauranteDTO.getEndereco().getCidade().getId()));
		}
		
		restauranteDTO.add(algaLinks.linkToProdutos(restauranteDTO.getId(), "produtos"));
		
		return restauranteDTO;
		
		//Jeito Antigo sem o ModelMapper
//		CozinhaDTO cozinhaDTO = new CozinhaDTO();
//		cozinhaDTO.setId(restaurante.getCozinha().getId());
//		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
//
//		RestauranteDTO restauranteDTO = new RestauranteDTO();
//		restauranteDTO.setId(restaurante.getId());
//		restauranteDTO.setNome(restaurante.getNome());
//		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
//		restauranteDTO.setCozinha(cozinhaDTO);
//		return restauranteDTO;
	}
	
}
