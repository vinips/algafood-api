package com.vinips.algafood.api.v1.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.v1.model.dto.RestauranteResumoDTO;
import com.vinips.algafood.api.v1.openapi.dto.RestaurantesResumoDTOOpenApi.RestauranteResumoEmbeddedDTOOpenApi;

import io.swagger.annotations.ApiModel;

@ApiModel("RestaurantesResumoDTO")
public class RestaurantesResumoDTOOpenApi extends CollectionModelOpenApi<RestauranteResumoEmbeddedDTOOpenApi> {

	@ApiModel("RestaurantesResumoEmbeddedDTO")
	protected class RestauranteResumoEmbeddedDTOOpenApi {

		private List<RestauranteResumoDTO> restaurantes;

		public List<RestauranteResumoDTO> getRestaurantes() {
			return restaurantes;
		}

		public void setRestaurantes(List<RestauranteResumoDTO> restaurantes) {
			this.restaurantes = restaurantes;
		}

	}

}
