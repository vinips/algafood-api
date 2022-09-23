package com.vinips.algafood.api.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.model.dto.UsuarioDTO;
import com.vinips.algafood.api.openapi.dto.UsuariosDTOOpenApi.UsuarioEmbeddedDTOOpenApi;

import io.swagger.annotations.ApiModel;

@ApiModel("UsuariosDTO")
public class UsuariosDTOOpenApi extends CollectionModelOpenApi<UsuarioEmbeddedDTOOpenApi> {

	@ApiModel("UsuariosEmbeddedDTO")
	protected class UsuarioEmbeddedDTOOpenApi {

		private List<UsuarioDTO> usuarios;

		public List<UsuarioDTO> getUsuarios() {
			return usuarios;
		}

		public void setUsuarios(List<UsuarioDTO> usuarios) {
			this.usuarios = usuarios;
		}

	}

}
