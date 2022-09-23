package com.vinips.algafood.api.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.model.dto.CozinhaDTO;

import io.swagger.annotations.ApiModel;

@ApiModel("CozinhasDTO")
public class CozinhasDTOOpenApi extends CollectionModelOpenApi {

	private CozinhaEmbeddedDTOOpenApi _embedded;

	private PagedModelOpenApi page;

	@ApiModel("CozinhasEmbeddedDTO")
	protected class CozinhaEmbeddedDTOOpenApi {

		private List<CozinhaDTO> cozinhas;

		public List<CozinhaDTO> getCozinhas() {
			return cozinhas;
		}

		public void setCozinhas(List<CozinhaDTO> cozinhas) {
			this.cozinhas = cozinhas;
		}

	}

	public CozinhaEmbeddedDTOOpenApi get_embedded() {
		return _embedded;
	}

	public void set_embedded(CozinhaEmbeddedDTOOpenApi _embedded) {
		this._embedded = _embedded;
	}

	public PagedModelOpenApi getPage() {
		return page;
	}

	public void setPage(PagedModelOpenApi page) {
		this.page = page;
	}

}
