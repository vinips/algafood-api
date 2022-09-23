package com.vinips.algafood.api.openapi.dto;

import org.springframework.hateoas.Links;

public class CollectionModelOpenApi<T> {

	private T _embedded;
	
	private Links _links;

	public Links get_links() {
		return _links;
	}

	public void set_links(Links _links) {
		this._links = _links;
	}

	public T get_embedded() {
		return _embedded;
	}

	public void set_embedded(T _embedded) {
		this._embedded = _embedded;
	}

}
