package com.vinips.algafood.api.model.dto;

import io.swagger.annotations.ApiModelProperty;

public class CidadeDTO {
	
	//Serve para alterar a propriedade que é mostrada na documentação que o Swagger gera automaticamente pra gente. Módulo 18
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Uberlândia")
	private String nome;
	
	private EstadoDTO estado;

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

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

	
}
