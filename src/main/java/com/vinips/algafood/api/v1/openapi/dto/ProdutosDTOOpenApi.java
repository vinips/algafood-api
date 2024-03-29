package com.vinips.algafood.api.v1.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.v1.model.dto.ProdutoDTO;
import com.vinips.algafood.api.v1.openapi.dto.ProdutosDTOOpenApi.ProdutoEmbeddedDTOOpenApi;

import io.swagger.annotations.ApiModel;

@ApiModel("ProdutosDTO")
public class ProdutosDTOOpenApi extends CollectionModelOpenApi<ProdutoEmbeddedDTOOpenApi> {

	@ApiModel("ProdutosEmbeddedDTO")
	protected class ProdutoEmbeddedDTOOpenApi {

		private List<ProdutoDTO> produtos;

		public List<ProdutoDTO> getProdutos() {
			return produtos;
		}

		public void setProdutos(List<ProdutoDTO> produtos) {
			this.produtos = produtos;
		}

	}

}
