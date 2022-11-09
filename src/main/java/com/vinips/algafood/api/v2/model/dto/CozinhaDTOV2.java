package com.vinips.algafood.api.v2.model.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("CozinhaModel")
@Relation(collectionRelation = "cozinhas")
public class CozinhaDTOV2 extends RepresentationModel<CozinhaDTOV2>{

	@ApiModelProperty(example = "1")
	private Long idCozinha;
	
	@ApiModelProperty(example = "Japonesa")
	private String nomeCozinha;

	public Long getIdCozinha() {
		return idCozinha;
	}

	public void setIdCozinha(Long idCozinha) {
		this.idCozinha = idCozinha;
	}

	public String getNomeCozinha() {
		return nomeCozinha;
	}

	public void setNomeCozinha(String nomeCozinha) {
		this.nomeCozinha = nomeCozinha;
	}
	
	
}
