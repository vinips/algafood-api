package com.vinips.algafood.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class UsuarioInput {
	
	@ApiModelProperty(example = "Jonas", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "jonas@hotmail.com", required = true)
	@NotBlank
	@Email
	private String email;
	
	@ApiModelProperty(example = "1234@", required = true)
	@NotBlank
	private String senha;
	
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
