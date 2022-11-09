package com.vinips.algafood.api.v1.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.v1.AlgaLinks;
import com.vinips.algafood.api.v1.controller.RestauranteController;
import com.vinips.algafood.api.v1.model.dto.RestauranteResumoDTO;
import com.vinips.algafood.domain.model.Restaurante;

@Component
public class RestauranteResumoDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteResumoDTO>{

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteResumoDTOAssembler() {
		super(RestauranteController.class, RestauranteResumoDTO.class);
	}
	
	public RestauranteResumoDTO toModel(Restaurante restaurante) {
		RestauranteResumoDTO restauranteResumoDTO = createModelWithId(restaurante.getId(), restaurante);
		
		modelMapper.map(restaurante, restauranteResumoDTO);
		
		restauranteResumoDTO.add(algaLinks.linkToRestaurantes("restaurantes"));
		
		restauranteResumoDTO.getCozinha().add(algaLinks.linkToCozinha(restauranteResumoDTO.getCozinha().getId()));
		
		return restauranteResumoDTO;
	}
	
	@Override
	public CollectionModel<RestauranteResumoDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToRestaurantes());
	}
	
}
