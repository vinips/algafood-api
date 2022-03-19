package com.vinips.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.model.assembler.PedidoDTOAssembler;
import com.vinips.algafood.api.model.dto.PedidoDTO;
import com.vinips.algafood.domain.model.Pedido;
import com.vinips.algafood.domain.repository.PedidoRepository;
import com.vinips.algafood.domain.service.CadastroPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroPedidoService cadastroPedido;
	
	@Autowired
	private PedidoDTOAssembler pedidoAssembler;
	
	@GetMapping
	public List<PedidoDTO> listar(){
		List<Pedido> pedidos = pedidoRepository.findAll();
	
		return pedidoAssembler.toCollectionDTO(pedidos);
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoDTO buscar(@PathVariable Long pedidoId){
		return pedidoAssembler.toDTO(cadastroPedido.buscarOuFalhar(pedidoId));
		
	}

}
