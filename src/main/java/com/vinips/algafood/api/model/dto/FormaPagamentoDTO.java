package com.vinips.algafood.api.model.dto;

import io.swagger.annotations.ApiModelProperty;

public class FormaPagamentoDTO {
	
	//Olhar CidadeDTO para mais explicações;
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Dinheiro")
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
