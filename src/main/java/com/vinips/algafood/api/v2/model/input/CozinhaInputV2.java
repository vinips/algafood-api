package com.vinips.algafood.api.v2.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("CozinhaInput")
public class CozinhaInputV2 {
	
	@ApiModelProperty(example = "Japonesa", required = true)
	@NotBlank
	private String nomeCozinha;
	
	public String getNomeCozinha() {
		return nomeCozinha;
	}
	
	public void setNomeCozinha(String nome) {
		this.nomeCozinha = nome;
	}
	

}
