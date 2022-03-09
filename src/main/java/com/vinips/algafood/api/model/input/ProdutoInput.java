package com.vinips.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

public class ProdutoInput {
	
	@NotBlank
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
