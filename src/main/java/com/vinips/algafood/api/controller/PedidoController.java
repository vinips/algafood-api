package com.vinips.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
import com.vinips.algafood.domain.repository.filter.PedidoFilter;
import com.vinips.algafood.domain.service.CadastroPedidoService;
import com.vinips.algafood.infrastructure.repository.spec.PedidoSpecs;

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
	public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable){
		Page<Pedido> pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
	
		List<PedidoResumoDTO> pedidosResumoDTO = pedidoResumoAssembler.toCollectionDTO(pedidos.getContent());
		
		Page<PedidoResumoDTO> pedidosResumoDTOPage = new PageImpl<>(pedidosResumoDTO, pageable, pedidos.getTotalElements());
		
		return pedidosResumoDTOPage;
	}
	
	@GetMapping("/{codigoPedido}")
	public PedidoDTO buscar(@PathVariable String codigoPedido){
		return pedidoAssembler.toDTO(cadastroPedido.buscarOuFalhar(codigoPedido));
		
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
	
	@PutMapping("/{codigoPedido}/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable String codigoPedido) {
		cadastroPedido.confirmar(codigoPedido);
	}
	
	@PutMapping("/{codigoPedido}/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregar(@PathVariable String codigoPedido) {
		cadastroPedido.entregar(codigoPedido);
	}
	
	@PutMapping("/{codigoPedido}/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable String codigoPedido) {
		cadastroPedido.cancelar(codigoPedido);
	}

}
