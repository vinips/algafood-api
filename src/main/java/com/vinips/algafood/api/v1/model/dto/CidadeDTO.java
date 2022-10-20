package com.vinips.algafood.api.v1.model.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;

//Nome que aparece no embedded do CollectionModel quando retornamos um Json
@Relation(collectionRelation = "cidades")
public class CidadeDTO extends RepresentationModel<CidadeDTO>{
	
	//Serve para alterar a propriedade que é mostrada na documentação que o Swagger gera automaticamente pra gente. Módulo 18
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Uberlândia")
	private String nome;
	
	private EstadoDTO estado;

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

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

	
}
