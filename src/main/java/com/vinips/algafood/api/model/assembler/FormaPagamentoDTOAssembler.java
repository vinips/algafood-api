package com.vinips.algafood.api.model.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.model.dto.FormaPagamentoDTO;
import com.vinips.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
	
	public List<FormaPagamentoDTO> toCollectionDTO(Collection<FormaPagamento> formaPagamentoList) {
		return formaPagamentoList.stream().map(formaPagamento -> toDTO(formaPagamento)).collect(Collectors.toList());
	}
	
}
