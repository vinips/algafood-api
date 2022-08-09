package com.vinips.algafood.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.controller.CidadeController;
import com.vinips.algafood.api.controller.FormaPagamentoController;
import com.vinips.algafood.api.controller.PedidoController;
import com.vinips.algafood.api.controller.RestauranteController;
import com.vinips.algafood.api.controller.RestauranteProdutoController;
import com.vinips.algafood.api.controller.UsuarioController;
import com.vinips.algafood.api.model.dto.PedidoDTO;
import com.vinips.algafood.domain.model.Pedido;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoDTOAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}

	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoDTO = createModelWithId(pedido.getId(), pedido);

		modelMapper.map(pedido, pedidoDTO);
		
		pedidoDTO.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withRel("pedidos"));
		
		pedidoDTO.getRestaurante().add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(RestauranteController.class).buscar(pedidoDTO.getRestaurante().getId()))
				.withSelfRel());
		
		pedidoDTO.getCliente()
				.add(WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder.methodOn(UsuarioController.class).buscar(pedidoDTO.getCliente().getId()))
						.withSelfRel());

		pedidoDTO.getFormaPagamento()
				.add(WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder.methodOn(FormaPagamentoController.class).buscar(pedidoDTO.getFormaPagamento().getId()))
						.withSelfRel());
		
		pedidoDTO.getEnderecoEntrega().getCidade()
				.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
						.buscar(pedidoDTO.getEnderecoEntrega().getCidade().getId())).withSelfRel());
		
		pedidoDTO.getItens().forEach(item -> {
			item.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class)
					.buscar(pedidoDTO.getRestaurante().getId(), item.getProdutoId())).withRel("produto"));
		});
		
		return pedidoDTO;
	}
	
	
//	public List<PedidoDTO> toCollectionDTO(Collection<Pedido> pedidoList) {
//		return pedidoList.stream().map(pedido -> toDTO(pedido)).collect(Collectors.toList());
//	}
	
}
