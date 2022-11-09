package com.vinips.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class EnderecoInput {
	
	@ApiModelProperty(example = "88125-325", required = true)
	@NotBlank
	private String cep;
	
	@ApiModelProperty(example = "Rua das Américas", required = true)
	@NotBlank
	private String logradouro;
	
	@ApiModelProperty(example = "121", required = true)
	@NotBlank
	private String numero;
	
	@ApiModelProperty(example = "Casa")
	private String complemento;
	
	@ApiModelProperty(example = "Betaville", required = true)
	@NotBlank
	private String bairro;
	
	@Valid
	@NotNull
	private CidadeIdInput cidade;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public CidadeIdInput getCidade() {
		return cidade;
	}

	public void setCidade(CidadeIdInput cidade) {
		this.cidade = cidade;
	}
	
	

}
