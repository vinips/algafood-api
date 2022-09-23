package com.vinips.algafood.api.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.model.dto.EstadoDTO;
import com.vinips.algafood.api.openapi.dto.EstadosDTOOpenApi.EstadoEmbeddedDTOOpenApi;

import io.swagger.annotations.ApiModel;

@ApiModel("EstadosDTO")
public class EstadosDTOOpenApi extends CollectionModelOpenApi<EstadoEmbeddedDTOOpenApi> {


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

}
