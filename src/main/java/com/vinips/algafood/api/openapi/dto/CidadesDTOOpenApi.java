package com.vinips.algafood.api.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.model.dto.CidadeDTO;

import io.swagger.annotations.ApiModel;

@ApiModel("CidadesDTO")
public class CidadesDTOOpenApi extends CollectionModelOpenApi {

	private CidadeEmbeddedDTOOpenApi _embedded;

	@ApiModel("CidadesEmbeddedDTO")
	public class CidadeEmbeddedDTOOpenApi {

		private List<CidadeDTO> cidades;

		public List<CidadeDTO> getCidades() {
			return cidades;
		}

		public void setCidades(List<CidadeDTO> cidades) {
			this.cidades = cidades;
		}

	}

	public CidadeEmbeddedDTOOpenApi get_embedded() {
		return _embedded;
	}

	public void set_embedded(CidadeEmbeddedDTOOpenApi _embedded) {
		this._embedded = _embedded;
	}

}
