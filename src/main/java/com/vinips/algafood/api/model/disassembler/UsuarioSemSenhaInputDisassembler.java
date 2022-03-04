package com.vinips.algafood.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.model.input.UsuarioSemSenhaInput;
import com.vinips.algafood.domain.model.Usuario;

@Component
public class UsuarioSemSenhaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Usuario toDomainModel(UsuarioSemSenhaInput usuarioSemSenhaInput) {
		return modelMapper.map(usuarioSemSenhaInput, Usuario.class);
	}

	public void copyToDomainObject(UsuarioSemSenhaInput usuarioSemSenhaInput, Usuario usuario) {
		modelMapper.map(usuarioSemSenhaInput, usuario);
	}

}
