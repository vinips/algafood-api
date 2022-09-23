package com.vinips.algafood.api.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.model.dto.FormaPagamentoDTO;
import com.vinips.algafood.api.openapi.dto.FormasPagamentoDTOOpenApi.FormaPagamentoEmbeddedDTOOpenApi;

import io.swagger.annotations.ApiModel;

@ApiModel("FormasPagamentoDTO")
public class FormasPagamentoDTOOpenApi extends CollectionModelOpenApi<FormaPagamentoEmbeddedDTOOpenApi> {

	@ApiModel("FormasPagamentoEmbeddedDTO")
	protected class FormaPagamentoEmbeddedDTOOpenApi {

		private List<FormaPagamentoDTO> formasPagamento;

		public List<FormaPagamentoDTO> getFormasPagamento() {
			return formasPagamento;
		}

		public void setFormasPagamento(List<FormaPagamentoDTO> formasPagamento) {
			this.formasPagamento = formasPagamento;
		}

	}

}
