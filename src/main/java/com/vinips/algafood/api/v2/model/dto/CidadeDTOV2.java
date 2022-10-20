package com.vinips.algafood.api.v2.model.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;

//Nome que aparece no embedded do CollectionModel quando retornamos um Json
@Relation(collectionRelation = "cidades")
public class CidadeDTOV2 extends RepresentationModel<CidadeDTOV2>{
	
	//Serve para alterar a propriedade que é mostrada na documentação que o Swagger gera automaticamente pra gente. Módulo 18
	@ApiModelProperty(example = "1")
	private Long idCidade;
	
	@ApiModelProperty(example = "Uberlândia")
	private String nomeCidade;
	
	private Long idEstado;
	
	private String nomeEstado;

	public Long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	public Long getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(Long idCidade) {
		this.idCidade = idCidade;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

}
