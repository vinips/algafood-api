package com.vinips.algafood.api.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.model.dto.PermissaoDTO;

import io.swagger.annotations.ApiModel;

@ApiModel("PermissoesDTO")
public class PermissoesDTOOpenApi extends CollectionModelOpenApi {

	private PermissaoEmbeddedDTOOpenApi _embedded;

	@ApiModel("PermissoesEmbeddedDTO")
	protected class PermissaoEmbeddedDTOOpenApi {

		private List<PermissaoDTO> permissoes;

		public List<PermissaoDTO> getPermissoes() {
			return permissoes;
		}

		public void setPermissoes(List<PermissaoDTO> permissoes) {
			this.permissoes = permissoes;
		}

	}

	public PermissaoEmbeddedDTOOpenApi get_embedded() {
		return _embedded;
	}

	public void set_embedded(PermissaoEmbeddedDTOOpenApi _embedded) {
		this._embedded = _embedded;
	}

}
