package com.vinips.algafood.api.v1.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.v1.model.dto.PedidoResumoDTO;
import com.vinips.algafood.api.v1.openapi.dto.PedidosResumoDTOOpenApi.PedidoResumoEmbeddedDTOOpenApi;

import io.swagger.annotations.ApiModel;

@ApiModel("PedidosResumoDTO")
public class PedidosResumoDTOOpenApi extends CollectionModelOpenApi<PedidoResumoEmbeddedDTOOpenApi>{
	
	private PagedModelOpenApi page;
	
	@ApiModel("PedidosResumoEmbeddedDTO")
	protected class PedidoResumoEmbeddedDTOOpenApi {

		private List<PedidoResumoDTO> pedidos;

		public List<PedidoResumoDTO> getPedidos() {
			return pedidos;
		}

		public void setPedidos(List<PedidoResumoDTO> pedidos) {
			this.pedidos = pedidos;
		}

	}

	public PagedModelOpenApi getPage() {
		return page;
	}

	public void setPage(PagedModelOpenApi page) {
		this.page = page;
	}
	
}
