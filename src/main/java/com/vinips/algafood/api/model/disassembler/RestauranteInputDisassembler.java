package com.vinips.algafood.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.model.input.RestauranteInput;
import com.vinips.algafood.domain.model.Cozinha;
import com.vinips.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainModel(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);

		// Jeito Antigo sem o ModelMapper
//		Cozinha cozinha = new Cozinha();
//		cozinha.setId(restauranteInput.getCozinha().getId());
//		
//		Restaurante restaurante = new Restaurante();
//		restaurante.setNome(restauranteInput.getNome());
//		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
//		restaurante.setCozinha(cozinha);
//		
//		return restaurante;
	}

	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		// Precisa disso pois ao copiar uma cozinha para outra, o JPA entende que
		// estamos tentando mudar o id da cozinha no registro da cozinha no banco em vez
		// de estarmos apenas trocando a referência de cozinha dentro de Restaurante.
		
		restaurante.setCozinha(new Cozinha());

		modelMapper.map(restauranteInput, restaurante);
	}

}
