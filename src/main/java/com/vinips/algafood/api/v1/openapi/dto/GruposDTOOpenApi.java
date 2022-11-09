package com.vinips.algafood.api.v1.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.v1.model.dto.GrupoDTO;
import com.vinips.algafood.api.v1.openapi.dto.GruposDTOOpenApi.GrupoEmbeddedDTOOpenApi;

import io.swagger.annotations.ApiModel;

@ApiModel("GruposDTO")
public class GruposDTOOpenApi extends CollectionModelOpenApi<GrupoEmbeddedDTOOpenApi> {

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


}
