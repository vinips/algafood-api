package com.vinips.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PermissaoNaoEncontradaException (String msg) {
		super(msg);
	}
	
	public PermissaoNaoEncontradaException (Long permissaoId) {
		this(String.format("Permissão de código %d não existe", permissaoId));
	}
	
}
