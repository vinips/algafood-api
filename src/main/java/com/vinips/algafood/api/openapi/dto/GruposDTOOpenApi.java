package com.vinips.algafood.api.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.model.dto.GrupoDTO;

import io.swagger.annotations.ApiModel;

@ApiModel("GruposDTO")
public class GruposDTOOpenApi extends CollectionModelOpenApi {

	private GrupoEmbeddedDTOOpenApi _embedded;

	@ApiModel("GruposEmbeddedDTO")
	protected class GrupoEmbeddedDTOOpenApi {

		private List<GrupoDTO> grupos;

		public List<GrupoDTO> getGrupos() {
			return grupos;
		}

		public void setGrupos(List<GrupoDTO> grupos) {
			this.grupos = grupos;
		}

	}

	public GrupoEmbeddedDTOOpenApi get_embedded() {
		return _embedded;
	}

	public void set_embedded(GrupoEmbeddedDTOOpenApi _embedded) {
		this._embedded = _embedded;
	}

}
