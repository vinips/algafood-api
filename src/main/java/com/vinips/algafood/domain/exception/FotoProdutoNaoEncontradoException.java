package com.vinips.algafood.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FotoProdutoNaoEncontradoException (String msg) {
		super(msg);
	}
	
	public FotoProdutoNaoEncontradoException (Long restauranteId, Long produtoId) {
		this(String.format("Foto do produto %d do restaurante de código %d não existe", produtoId ,restauranteId));
	}
	
}
