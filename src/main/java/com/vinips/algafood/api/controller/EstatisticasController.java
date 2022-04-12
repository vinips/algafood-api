package com.vinips.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.domain.filter.VendaDiariaFilter;
import com.vinips.algafood.domain.model.dto.VendaDiaria;
import com.vinips.algafood.domain.service.VendaQueryService;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController {

	@Autowired
	private VendaQueryService vendaQueryService;

	@GetMapping("/vendas-diarias")
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
		return vendaQueryService.consultarVendasDiarias(filter, timeOffSet);
	}

}
