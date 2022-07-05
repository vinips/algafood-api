package com.vinips.algafood.api.model.dto;

import io.swagger.annotations.ApiModelProperty;

public class GrupoDTO {
	
	//Olhar CidadeDTO para mais explicações;
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Gerência")
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
