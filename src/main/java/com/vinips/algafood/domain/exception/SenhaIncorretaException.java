package com.vinips.algafood.domain.exception;

//Outra forma de fazer o ResponseStatus quando da Exception. utilizando junto do throw new CLASSE_EXCEPTION
//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SenhaIncorretaException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public SenhaIncorretaException(String msg) {
		super(msg);
	}

}
