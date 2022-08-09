package com.vinips.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.vinips.algafood.api.model.assembler.PedidoDTOAssembler;
import com.vinips.algafood.api.model.assembler.PedidoResumoDTOAssembler;
import com.vinips.algafood.api.model.disassembler.PedidoInputDisassembler;
import com.vinips.algafood.api.model.dto.PedidoDTO;
import com.vinips.algafood.api.model.dto.PedidoResumoDTO;
import com.vinips.algafood.api.model.input.PedidoInput;
import com.vinips.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.vinips.algafood.core.data.PageableTranslator;
import com.vinips.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.vinips.algafood.domain.exception.NegocioException;
import com.vinips.algafood.domain.filter.PedidoFilter;
import com.vinips.algafood.domain.model.Pedido;
import com.vinips.algafood.domain.model.Usuario;
import com.vinips.algafood.domain.repository.PedidoRepository;
import com.vinips.algafood.domain.service.CadastroPedidoService;
import com.vinips.algafood.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi{
	
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
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@GetMapping
	public PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable){
		pageable = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
		
		PagedModel<PedidoResumoDTO> pedidosResumoPagedDTO = pagedResourcesAssembler
				.toModel(pedidosPage, pedidoResumoAssembler);
	
//		List<PedidoResumoDTO> pedidosResumoDTO = pedidoResumoAssembler.toCollectionDTO(pedidos.getContent());
//		
//		Page<PedidoResumoDTO> pedidosResumoDTOPage = new PageImpl<>(pedidosResumoDTO, pageable, pedidos.getTotalElements());
		
		return pedidosResumoPagedDTO;
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
	
	private Pageable traduzirPageable(Pageable pageable) {
		var mapeamento = ImmutableMap.of(
				"codigo", "codigo",
				"restaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal"
				);
		
		return PageableTranslator.translate(pageable, mapeamento); 
	}

}
