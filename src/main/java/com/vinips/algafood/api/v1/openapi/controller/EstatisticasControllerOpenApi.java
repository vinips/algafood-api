package com.vinips.algafood.api.v1.openapi.controller;

import java.util.List;

import com.vinips.algafood.api.v1.model.dto.EstatisticasDTO;
import com.vinips.algafood.domain.filter.VendaDiariaFilter;
import com.vinips.algafood.domain.model.dto.VendaDiaria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {
	
	@ApiOperation(value = "Estatísticas", hidden = true)
	public EstatisticasDTO estatisticas();

	@ApiOperation("Relatório das vendas diárias")
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter,
			@ApiParam(value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC", defaultValue = "+00:00") String timeOffSet);
}
