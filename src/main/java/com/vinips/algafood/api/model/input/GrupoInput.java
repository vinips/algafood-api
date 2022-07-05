package com.vinips.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class GrupoInput {
	
	@ApiModelProperty(example = "Gerência", required = true)
	@NotBlank
	private String nome;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

}
