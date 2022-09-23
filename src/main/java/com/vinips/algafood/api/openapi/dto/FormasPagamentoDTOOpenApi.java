package com.vinips.algafood.api.openapi.dto;

import java.util.List;

import com.vinips.algafood.api.model.dto.FormaPagamentoDTO;

import io.swagger.annotations.ApiModel;

@ApiModel("FormasPagamentoDTO")
public class FormasPagamentoDTOOpenApi extends CollectionModelOpenApi {

	private FormaPagamentoEmbeddedDTOOpenApi _embedded;

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

	public FormaPagamentoEmbeddedDTOOpenApi get_embedded() {
		return _embedded;
	}

	public void set_embedded(FormaPagamentoEmbeddedDTOOpenApi _embedded) {
		this._embedded = _embedded;
	}

}
