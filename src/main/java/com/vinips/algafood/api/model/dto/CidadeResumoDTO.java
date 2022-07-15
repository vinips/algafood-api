package com.vinips.algafood.api.model.dto;

import io.swagger.annotations.ApiModelProperty;

public class CidadeResumoDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Palhoça")
	private String nome;
	
	@ApiModelProperty(example = "Santa Catarina")
	private String estado;

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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	
}
