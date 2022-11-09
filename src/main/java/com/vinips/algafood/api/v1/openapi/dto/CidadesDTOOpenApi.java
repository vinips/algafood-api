package com.vinips.algafood.api.v1.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.v1.model.dto.CidadeDTO;
import com.vinips.algafood.api.v1.openapi.dto.CidadesDTOOpenApi.CidadeEmbeddedDTOOpenApi;

import io.swagger.annotations.ApiModel;

@ApiModel("CidadesDTO")
public class CidadesDTOOpenApi extends CollectionModelOpenApi<CidadeEmbeddedDTOOpenApi> {

	@ApiModel("CidadesEmbeddedDTO")
	protected class CidadeEmbeddedDTOOpenApi {

		private List<CidadeDTO> cidades;

		public List<CidadeDTO> getCidades() {
			return cidades;
		}

		public void setCidades(List<CidadeDTO> cidades) {
			this.cidades = cidades;
		}

	}

}
