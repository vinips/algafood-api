package com.vinips.algafood.api.model.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class ProdutoDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Bobó de Camarão")
	private String nome;
	
	@ApiModelProperty(example = "Delicioso Bobó de camarão na chapa")
	private String descricao;
	
	@ApiModelProperty(example = "13.69")
	private BigDecimal preco;
	
	@ApiModelProperty(example = "true")
	private Boolean ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	
}
