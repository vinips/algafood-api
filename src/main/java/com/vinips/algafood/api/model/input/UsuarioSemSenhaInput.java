package com.vinips.algafood.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class UsuarioSemSenhaInput {
	
	@ApiModelProperty(example = "Jonas", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "jonas@hotmail.com", required = true)
	@NotBlank
	@Email
	private String email;
	
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
