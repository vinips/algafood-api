package com.vinips.algafood.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.model.dto.CozinhaDTO;
import com.vinips.algafood.domain.model.Cozinha;

@Component
public class CozinhaDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public CozinhaDTO toDTO(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaDTO.class);
	}
	
	public List<CozinhaDTO> toCollectionDTO(List<Cozinha> cozinhaList) {
		return cozinhaList.stream().map(cozinha -> toDTO(cozinha)).collect(Collectors.toList());
	}
	
}
