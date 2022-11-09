package com.vinips.algafood.api.v1.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;

public class RestauranteInput {
	
	@ApiModelProperty(example = "Sabores do Agreste", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "2.99", value = "Frete deve ser zero ou maior", required = true)
	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaIdInput cozinha;
	
	@Valid
	@NotNull
	private EnderecoInput endereco;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public BigDecimal getTaxaFrete() {
		return taxaFrete;
	}
	
	public void setTaxaFrete(BigDecimal taxaFrete) {
		this.taxaFrete = taxaFrete;
	}
	
	public CozinhaIdInput getCozinha() {
		return cozinha;
	}
	
	public void setCozinha(CozinhaIdInput cozinha) {
		this.cozinha = cozinha;
	}

	public EnderecoInput getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoInput endereco) {
		this.endereco = endereco;
	}
	
	

}
