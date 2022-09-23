package com.vinips.algafood.api.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.model.dto.PermissaoDTO;
import com.vinips.algafood.api.openapi.dto.PermissoesDTOOpenApi.PermissaoEmbeddedDTOOpenApi;

import io.swagger.annotations.ApiModel;

@ApiModel("PermissoesDTO")
public class PermissoesDTOOpenApi extends CollectionModelOpenApi<PermissaoEmbeddedDTOOpenApi> {

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

}
