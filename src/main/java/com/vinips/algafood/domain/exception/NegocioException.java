package com.vinips.algafood.domain.exception;

//Outra forma de fazer o ResponseStatus quando da Exception. utilizando junto do throw new CLASSE_EXCEPTION
//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NegocioException(String msg) {
		super(msg);
	}
	
	public NegocioException(String msg, Throwable causa) {
		super(msg, causa);
	}

}
