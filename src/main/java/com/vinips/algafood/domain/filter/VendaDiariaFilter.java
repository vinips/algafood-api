package com.vinips.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModelProperty;

public class VendaDiariaFilter {
	
	@ApiModelProperty(example = "1", value = "ID do Restaurante para filtro da pesquisa")
	private Long restauranteId;
	
	@ApiModelProperty(example = "2022-04-11T23:42:40Z", value = "Data inicial do pedido")
	//Necessária essa anotation pro Spring conseguir converter String para OffsetDateTime
	//Quando a API recebe a requisição do front com essa informação.
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoInicio;
	
	@ApiModelProperty(example = "2022-04-11T23:42:40Z", value = "Data final do pedido")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;

	public Long getRestauranteId() {
		return restauranteId;
	}

	public void setRestauranteId(Long restauranteId) {
		this.restauranteId = restauranteId;
	}

	public OffsetDateTime getDataCriacaoInicio() {
		return dataCriacaoInicio;
	}

	public void setDataCriacaoInicio(OffsetDateTime dataCriacaoInicio) {
		this.dataCriacaoInicio = dataCriacaoInicio;
	}

	public OffsetDateTime getDataCriacaoFim() {
		return dataCriacaoFim;
	}

	public void setDataCriacaoFim(OffsetDateTime dataCriacaoFim) {
		this.dataCriacaoFim = dataCriacaoFim;
	}	

}
