package com.vinips.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException (String msg) {
		super(msg);
	}
	
	public PedidoNaoEncontradoException (Long pedidoId) {
		this(String.format("Pedido de código %d não existe", pedidoId));
	}
	
}
