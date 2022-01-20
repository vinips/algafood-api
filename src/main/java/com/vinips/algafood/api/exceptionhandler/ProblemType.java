package com.vinips.algafood.api.exceptionhandler;

public enum ProblemType {
	
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível."),
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada."),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso."),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio.");

	private String uri;
	private String titulo;
	
	private ProblemType(String uri, String titulo) {
		this.uri = "https://algafood.com.br" +  uri;
		this.titulo = titulo;
	}

	public String getUri() {
		return uri;
	}

	public String getTitulo() {
		return titulo;
	}

}
