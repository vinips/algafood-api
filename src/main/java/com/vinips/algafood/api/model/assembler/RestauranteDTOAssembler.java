package com.vinips.algafood.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.model.dto.RestauranteDTO;
import com.vinips.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public RestauranteDTO toDTO(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDTO.class);
		
		
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
	
	public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restauranteList) {
		return restauranteList.stream().map(restaurante -> toDTO(restaurante)).collect(Collectors.toList());
	}
	
}
