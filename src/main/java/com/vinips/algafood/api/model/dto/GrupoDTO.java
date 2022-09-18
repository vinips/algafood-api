package com.vinips.algafood.api.model.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;

@Relation(collectionRelation = "grupos")
public class GrupoDTO extends RepresentationModel<GrupoDTO> {
	
	//Olhar CidadeDTO para mais explicações;
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Gerência")
	private String nome;

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
	
}
