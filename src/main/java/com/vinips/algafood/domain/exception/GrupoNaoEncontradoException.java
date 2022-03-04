package com.vinips.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public GrupoNaoEncontradoException (String msg) {
		super(msg);
	}
	
	public GrupoNaoEncontradoException (Long grupoId) {
		this(String.format("Grupo de código %d não existe", grupoId));
	}
	
}
