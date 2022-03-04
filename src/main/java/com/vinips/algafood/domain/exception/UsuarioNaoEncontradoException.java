package com.vinips.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException (String msg) {
		super(msg);
	}
	
	public UsuarioNaoEncontradoException (Long usuarioId) {
		this(String.format("Usuario de código %d não existe", usuarioId));
	}
	
}
