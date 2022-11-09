package com.vinips.algafood.api.v1.model.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;

//Nome que aparece no embedded do CollectionModel quando retornamos um Json
@Relation(collectionRelation = "usuarios")
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "José")
	private String nome;
	
	@ApiModelProperty(example = "jose_borges@gmail.com")
	private String email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
