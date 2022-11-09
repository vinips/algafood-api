package com.vinips.algafood.api.v1.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.v1.model.input.RestauranteInput;
import com.vinips.algafood.domain.model.Cidade;
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
		
		if (restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}

		modelMapper.map(restauranteInput, restaurante);
	}

}
