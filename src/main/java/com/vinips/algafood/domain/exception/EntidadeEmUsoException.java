package com.vinips.algafood.domain.exception;

//Outra forma de fazer o ResponseStatus quando da Exception. utilizando junto do throw new CLASSE_EXCEPTION
//@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntidadeEmUsoException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public EntidadeEmUsoException (String msg) {
		super(msg);
	}
	
}
