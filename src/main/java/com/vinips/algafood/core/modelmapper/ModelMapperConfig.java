package com.vinips.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vinips.algafood.api.model.dto.EnderecoDTO;
import com.vinips.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		//Associa que o Estado  do Endereço vai ser uma String com apenas o nome e não um objeto completo
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(), 
				(enderecoDtoDest, value) -> enderecoDtoDest.getCidade().setEstado(value));
		
		return modelMapper;
	}

}
