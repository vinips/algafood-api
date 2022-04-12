package com.vinips.algafood.domain.service;

import java.util.List;

import com.vinips.algafood.domain.filter.VendaDiariaFilter;
import com.vinips.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {
	
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter);

}
