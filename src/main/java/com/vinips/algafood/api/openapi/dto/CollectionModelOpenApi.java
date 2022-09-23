package com.vinips.algafood.api.openapi.dto;

import org.springframework.hateoas.Links;

public class CollectionModelOpenApi {

	private Links _links;

	public Links get_links() {
		return _links;
	}

	public void set_links(Links _links) {
		this._links = _links;
	}

}
