package com.vinips.algafood.api.openapi.dto;

import java.math.BigDecimal;

import com.vinips.algafood.api.model.dto.CozinhaDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("RestauranteBasicoDTO")
public class RestauranteBasicoDTOOpenApi {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Cantinho da Nhá")
	private String nome;
	
	@ApiModelProperty(example = "2.99")
	private BigDecimal taxaFrete;
	
	private CozinhaDTO cozinha;
	
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

	public BigDecimal getTaxaFrete() {
		return taxaFrete;
	}

	public void setTaxaFrete(BigDecimal taxaFrete) {
		this.taxaFrete = taxaFrete;
	}

	public CozinhaDTO getCozinha() {
		return cozinha;
	}

	public void setCozinha(CozinhaDTO cozinha) {
		this.cozinha = cozinha;
	}

	
}
