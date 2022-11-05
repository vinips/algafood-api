package com.vinips.algafood.api.v2.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.v1.openapi.dto.CollectionModelOpenApi;
import com.vinips.algafood.api.v1.openapi.dto.PagedModelOpenApi;
import com.vinips.algafood.api.v2.model.dto.CozinhaDTOV2;
import com.vinips.algafood.api.v2.openapi.dto.CozinhasDTOOpenApiV2.CozinhaEmbeddedDTOOpenApiV2;

import io.swagger.annotations.ApiModel;

@ApiModel("CozinhasDTO")
public class CozinhasDTOOpenApiV2 extends CollectionModelOpenApi<CozinhaEmbeddedDTOOpenApiV2> {

	private PagedModelOpenApi page;

	@ApiModel("CozinhasEmbeddedDTO")
	protected class CozinhaEmbeddedDTOOpenApiV2 {

		private List<CozinhaDTOV2> cozinhas;

		public List<CozinhaDTOV2> getCozinhas() {
			return cozinhas;
		}

		public void setCozinhas(List<CozinhaDTOV2> cozinhas) {
			this.cozinhas = cozinhas;
		}

	}

	public PagedModelOpenApi getPage() {
		return page;
	}

	public void setPage(PagedModelOpenApi page) {
		this.page = page;
	}

}
