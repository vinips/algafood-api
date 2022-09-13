package com.vinips.algafood.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.AlgaLinks;
import com.vinips.algafood.api.controller.PedidoController;
import com.vinips.algafood.api.model.dto.PedidoDTO;
import com.vinips.algafood.domain.model.Pedido;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PedidoDTOAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}

	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);

		modelMapper.map(pedido, pedidoDTO);
		
		//Adiciona os linksParams, para modelo Hateoas
		pedidoDTO.add(algaLinks.linkToPedidos("pedidos"));
		
		if(pedido.podeSerConfirmado()) {
			pedidoDTO.add(algaLinks.linkToConfirmacaoPedido(pedidoDTO.getCodigo(), "confirmar"));
		}
		
		if(pedido.podeSerEntregue()) {
			pedidoDTO.add(algaLinks.linkToEntregaPedido(pedidoDTO.getCodigo(), "entregar"));
		}
		
		if(pedido.podeSerCancelado()) {
			pedidoDTO.add(algaLinks.linkToCancelamentoPedido(pedidoDTO.getCodigo(), "cancelar"));
		}
		
		pedidoDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedidoDTO.getRestaurante().getId()));
		
		pedidoDTO.getCliente().add(algaLinks.linkToUsuario(pedidoDTO.getCliente().getId()));

		pedidoDTO.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedidoDTO.getFormaPagamento().getId()));
		
		pedidoDTO.getEnderecoEntrega().getCidade()
				.add(algaLinks.linkToCidade(pedidoDTO.getEnderecoEntrega().getCidade().getId()));
		
		pedidoDTO.getItens().forEach(item -> {
			item.add(algaLinks.linkToProduto(pedidoDTO.getRestaurante().getId(), item.getProdutoId(), "produto"));
		});
		
		return pedidoDTO;
	}
	
}
