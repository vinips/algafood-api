package com.vinips.algafood.domain.service;

import com.vinips.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
	
	byte [] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffSet);

}
