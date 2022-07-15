package com.vinips.algafood.api.model.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class ItemPedidoDTO {
	
	@ApiModelProperty(example = "1")
	private Long produtoId;
	
	@ApiModelProperty(example = "Bobó de Camarão")
	private String produtoNome;
	
	@ApiModelProperty(example = "3")
	private Integer quantidade;
	
	@ApiModelProperty(example = "27.99")
	private BigDecimal precoUnitario;
	
	@ApiModelProperty(example = "83.97")
	private BigDecimal precoTotal;
	
	@ApiModelProperty(example = "Sem cebola")
	private String observacao;

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public String getProdutoNome() {
		return produtoNome;
	}

	public void setProdutoNome(String produtoNome) {
		this.produtoNome = produtoNome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public BigDecimal getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	
}
