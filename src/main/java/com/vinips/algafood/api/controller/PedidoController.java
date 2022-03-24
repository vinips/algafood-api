package com.vinips.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.model.assembler.PedidoDTOAssembler;
import com.vinips.algafood.api.model.assembler.PedidoResumoDTOAssembler;
import com.vinips.algafood.api.model.disassembler.PedidoInputDisassembler;
import com.vinips.algafood.api.model.dto.PedidoDTO;
import com.vinips.algafood.api.model.dto.PedidoResumoDTO;
import com.vinips.algafood.api.model.input.PedidoInput;
import com.vinips.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.vinips.algafood.domain.exception.NegocioException;
import com.vinips.algafood.domain.model.Pedido;
import com.vinips.algafood.domain.model.Usuario;
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
	
	@Autowired
	private PedidoInputDisassembler pedidoDisassembler;
	
	@Autowired
	private PedidoResumoDTOAssembler pedidoResumoAssembler;
	
	@GetMapping
	public List<PedidoResumoDTO> listar(){
		List<Pedido> pedidos = pedidoRepository.findAll();
	
		return pedidoResumoAssembler.toCollectionDTO(pedidos);
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoDTO buscar(@PathVariable Long pedidoId){
		return pedidoAssembler.toDTO(cadastroPedido.buscarOuFalhar(pedidoId));
		
	}
	
	@PostMapping
	public PedidoDTO adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			 Pedido pedido = pedidoDisassembler.toDomainModel(pedidoInput);
			 
			 // TODO pegar usuário autenticado
			 pedido.setCliente(new Usuario());
			 pedido.getCliente().setId(1L);
			 
			 return pedidoAssembler.toDTO(cadastroPedido.salvar(pedido));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

}
