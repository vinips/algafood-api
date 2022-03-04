package com.vinips.algafood.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.model.input.GrupoInput;
import com.vinips.algafood.domain.model.Grupo;

@Component
public class GrupoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Grupo toDomainModel(GrupoInput grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}

	public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
			modelMapper.map(grupoInput, grupo);
	}

}
