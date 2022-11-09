package com.vinips.algafood.api.v2.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.v2.model.input.CidadeInputV2;
import com.vinips.algafood.domain.model.Cidade;
import com.vinips.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassemblerV2 {

	@Autowired
	private ModelMapper modelMapper;

	public Cidade toDomainModel(CidadeInputV2 cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}

	public void copyToDomainObject(CidadeInputV2 cidadeInput, Cidade cidade) {
		//Ver a Explicação no RestauranteInputDisassembler
		
		cidade.setEstado(new Estado());

		modelMapper.map(cidadeInput, cidade);
	}

}
