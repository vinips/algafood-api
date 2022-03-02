package com.vinips.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoEncontradaException (String msg) {
		super(msg);
	}
	
	public FormaPagamentoNaoEncontradaException (Long formaPagamentoId) {
		this(String.format("Forma de Pagamento de código %d não existe", formaPagamentoId));
	}
	
}
