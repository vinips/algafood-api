package com.vinips.algafood.api.v1.model.dto;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;

public class EnderecoDTO extends RepresentationModel<EnderecoDTO>{
	
	@ApiModelProperty(example = "88125-325")
	private String cep;
	
	@ApiModelProperty(example = "Rua das Américas")
	private String logradouro;
	
	@ApiModelProperty(example = "121")
	private String numero;
	
	@ApiModelProperty(example = "Casa")
	private String complemento;
	
	@ApiModelProperty(example = "Betaville")
	private String bairro;
	
	private CidadeResumoDTO cidade;

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

	public CidadeResumoDTO getCidade() {
		return cidade;
	}

	public void setCidade(CidadeResumoDTO cidade) {
		this.cidade = cidade;
	}
	
	

}
