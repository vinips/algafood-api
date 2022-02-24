package com.vinips.algafood.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.model.input.CidadeInput;
import com.vinips.algafood.domain.model.Cidade;
import com.vinips.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Cidade toDomainModel(CidadeInput cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}

	public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
		//Ver a Explicação no RestauranteInputDisassembler
		
		cidade.setEstado(new Estado());

		modelMapper.map(cidadeInput, cidade);
	}

}
