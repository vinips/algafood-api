package com.vinips.algafood.domain.exception;

//Outra forma de fazer o ResponseStatus quando da Exception. utilizando junto do throw new CLASSE_EXCEPTION
//@ResponseStatus(value = HttpStatus.NOT_FOUND) //, reason = "Entidade Não Encontrada")
public abstract class EntidadeNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontradaException (String msg) {
		super(msg);
	}
	
}
