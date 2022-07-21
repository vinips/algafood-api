package com.vinips.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class SenhaInput {
	
	@ApiModelProperty(example = "1234@", required = true)
	@NotBlank
	private String senhaAtual;
	
	@ApiModelProperty(example = "@4321", required = true)
	@NotBlank
	private String novaSenha;

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	

}
