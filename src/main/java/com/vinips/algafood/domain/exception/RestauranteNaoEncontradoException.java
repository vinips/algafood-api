package com.vinips.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException (String msg) {
		super(msg);
	}
	
	public RestauranteNaoEncontradoException (Long restauranteId) {
		this(String.format("Restaurante de código %d não existe", restauranteId));
	}
	
}
