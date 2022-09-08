package com.vinips.algafood.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.AlgaLinks;
import com.vinips.algafood.api.controller.RestauranteController;
import com.vinips.algafood.api.model.dto.RestauranteDTO;
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
		
		restauranteDTO.add(algaLinks.linkToRestauranteFormasPagamento(restauranteDTO.getId(), "formas-pagamento"));
		
		restauranteDTO.add(algaLinks.linkToRestauranteUsuarioResponsavel(restauranteDTO.getId(), "responsaveis"));
		
		restauranteDTO.getCozinha().add(algaLinks.linkToCozinha(restauranteDTO.getCozinha().getId()));
		
		if (restauranteDTO.getEndereco() != null) {
			if (restauranteDTO.getEndereco().getCidade() != null) {
				restauranteDTO.getEndereco().getCidade().add(algaLinks.linkToCidade(restauranteDTO.getEndereco().getCidade().getId()));
			}
		}
		
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
