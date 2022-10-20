package com.vinips.algafood.api.v1.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.v1.AlgaLinks;
import com.vinips.algafood.api.v1.controller.PedidoController;
import com.vinips.algafood.api.v1.model.dto.PedidoResumoDTO;
import com.vinips.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public PedidoResumoDTOAssembler() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}

	@Override
	public PedidoResumoDTO toModel(Pedido pedido) {
		PedidoResumoDTO resumoDTO = createModelWithId(pedido.getCodigo(), pedido);
		
		modelMapper.map(pedido, resumoDTO);
		
		resumoDTO.add(algaLinks.linkToPedidos("pedidos"));
		
		resumoDTO.getRestaurante().add(algaLinks.linkToRestaurante(resumoDTO.getRestaurante().getId()));
		
		resumoDTO.getCliente()
				.add(algaLinks.linkToUsuario(resumoDTO.getCliente().getId()));
		
		return resumoDTO;
	}
	
//	public List<PedidoResumoDTO> toCollectionDTO(Collection<Pedido> pedidoList) {
//		return pedidoList.stream().map(pedido -> toDTO(pedido)).collect(Collectors.toList());
//	}
	
}
