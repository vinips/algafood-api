package com.vinips.algafood.api.model.dto;

import io.swagger.annotations.ApiModelProperty;

public class PermissaoDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Visualizar")
	private String nome;
	
	@ApiModelProperty(example = "Permite apenas visualizar")
	private String descricao;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
}
