package com.vinips.algafood.api.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.model.dto.EstadoDTO;

import io.swagger.annotations.ApiModel;

@ApiModel("EstadosDTO")
public class EstadosDTOOpenApi extends CollectionModelOpenApi {

	private EstadoEmbeddedDTOOpenApi _embedded;

	@ApiModel("EstadosEmbeddedDTO")
	protected class EstadoEmbeddedDTOOpenApi {

		private List<EstadoDTO> estados;

		public List<EstadoDTO> getEstados() {
			return estados;
		}

		public void setEstados(List<EstadoDTO> estados) {
			this.estados = estados;
		}

	}

	public EstadoEmbeddedDTOOpenApi get_embedded() {
		return _embedded;
	}

	public void set_embedded(EstadoEmbeddedDTOOpenApi _embedded) {
		this._embedded = _embedded;
	}

}
