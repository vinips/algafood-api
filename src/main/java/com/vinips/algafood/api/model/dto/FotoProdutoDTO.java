package com.vinips.algafood.api.model.dto;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;

public class FotoProdutoDTO extends RepresentationModel<FotoProdutoDTO>{

	@ApiModelProperty(example = "temaki.png")
	private String nomeArquivo;
	
	@ApiModelProperty(example = "temaki")
	private String descricao;
	
	@ApiModelProperty(example = "image/png")
	private String contentType;
	
	@ApiModelProperty(example = "100254")
	private Long tamanho;

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getTamanho() {
		return tamanho;
	}

	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}
	
	
	
}
