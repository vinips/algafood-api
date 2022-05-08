package com.vinips.algafood.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.model.dto.FotoProdutoDTO;
import com.vinips.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public FotoProdutoDTO toDTO(FotoProduto fotoProduto) {
		return modelMapper.map(fotoProduto, FotoProdutoDTO.class);
	}
}
