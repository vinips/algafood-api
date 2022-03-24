package com.vinips.algafood.api.model.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.model.dto.PedidoResumoDTO;
import com.vinips.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoDTO toDTO(Pedido pedido) {
		return modelMapper.map(pedido, PedidoResumoDTO.class);
	}
	
	public List<PedidoResumoDTO> toCollectionDTO(Collection<Pedido> pedidoList) {
		return pedidoList.stream().map(pedido -> toDTO(pedido)).collect(Collectors.toList());
	}
	
}
