package com.vinips.algafood.api.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.vinips.algafood.api.model.view.RestauranteView;

import io.swagger.annotations.ApiModelProperty;

public class CozinhaDTO {

	@ApiModelProperty(example = "1")
	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	
	@ApiModelProperty(example = "Japonesa")
	@JsonView(RestauranteView.Resumo.class)
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
