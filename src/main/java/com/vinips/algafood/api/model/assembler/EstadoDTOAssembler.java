package com.vinips.algafood.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.model.dto.EstadoDTO;
import com.vinips.algafood.domain.model.Estado;

@Component
public class EstadoDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public EstadoDTO toDTO(Estado estado) {
		return modelMapper.map(estado, EstadoDTO.class);
	}
	
	public List<EstadoDTO> toCollectionDTO(List<Estado> estadoList) {
		return estadoList.stream().map(estado -> toDTO(estado)).collect(Collectors.toList());
	}
	
}