package com.vinips.algafood.api.model.dto;

import io.swagger.annotations.ApiModelProperty;

public class UsuarioDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "José")
	private String nome;
	
	@ApiModelProperty(example = "jose_borges@gmail.com")
	private String email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
