package com.vinips.algafood.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.controller.PedidoController;
import com.vinips.algafood.api.controller.RestauranteController;
import com.vinips.algafood.api.controller.UsuarioController;
import com.vinips.algafood.api.model.dto.PedidoResumoDTO;
import com.vinips.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO>{

	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoDTOAssembler() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}

	@Override
	public PedidoResumoDTO toModel(Pedido pedido) {
		PedidoResumoDTO resumoDTO = createModelWithId(pedido.getCodigo(), pedido);
		
		modelMapper.map(pedido, resumoDTO);
		
		resumoDTO.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withRel("pedidos"));
		
		resumoDTO.getRestaurante().add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(RestauranteController.class).buscar(resumoDTO.getRestaurante().getId()))
				.withSelfRel());
		
		resumoDTO.getCliente()
				.add(WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder.methodOn(UsuarioController.class).buscar(resumoDTO.getCliente().getId()))
						.withSelfRel());
		
		return resumoDTO;
	}
	
//	public List<PedidoResumoDTO> toCollectionDTO(Collection<Pedido> pedidoList) {
//		return pedidoList.stream().map(pedido -> toDTO(pedido)).collect(Collectors.toList());
//	}
	
}
