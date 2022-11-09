package com.vinips.algafood.api.v2.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.v1.openapi.dto.CollectionModelOpenApi;
import com.vinips.algafood.api.v2.model.dto.CidadeDTOV2;
import com.vinips.algafood.api.v2.openapi.dto.CidadesDTOOpenApiV2.CidadeEmbeddedDTOOpenApiV2;

import io.swagger.annotations.ApiModel;

@ApiModel("CidadesDTO")
public class CidadesDTOOpenApiV2 extends CollectionModelOpenApi<CidadeEmbeddedDTOOpenApiV2> {

	@ApiModel("CidadesEmbeddedDTO")
	protected class CidadeEmbeddedDTOOpenApiV2 {

		private List<CidadeDTOV2> cidades;

		public List<CidadeDTOV2> getCidades() {
			return cidades;
		}

		public void setCidades(List<CidadeDTOV2> cidades) {
			this.cidades = cidades;
		}

	}

}
