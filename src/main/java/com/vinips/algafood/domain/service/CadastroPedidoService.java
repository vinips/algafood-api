package com.vinips.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinips.algafood.domain.exception.PedidoNaoEncontradoException;
import com.vinips.algafood.domain.model.Pedido;
import com.vinips.algafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	//Boa prática anotar os métodos que fazem manipulação de dados com o @Transactional para não ocorrer erros
	@Transactional
	public Pedido salvar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
	
	//Aqui ele vai retornar uma Forma de Pagamento
	//Caso não encontre a Forma de Pagamento, vai lançar uma Exception.
	public Pedido buscarOuFalhar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId).orElseThrow(
				() -> new PedidoNaoEncontradoException(pedidoId));
	}

}
