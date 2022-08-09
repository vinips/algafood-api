package com.vinips.algafood.api.model.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;

@Relation(collectionRelation = "formasPagamento")
public class FormaPagamentoDTO extends RepresentationModel<FormaPagamentoDTO> {
	
	//Olhar CidadeDTO para mais explicações;
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Dinheiro")
	private String descricao;

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
	
}
