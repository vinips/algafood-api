package com.vinips.algafood.api.exceptionhandler;

public enum ProblemType {
	
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro Inválido"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível."),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
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
