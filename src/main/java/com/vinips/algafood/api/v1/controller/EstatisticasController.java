package com.vinips.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.v1.AlgaLinks;
import com.vinips.algafood.api.v1.model.dto.EstatisticasDTO;
import com.vinips.algafood.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.vinips.algafood.domain.filter.VendaDiariaFilter;
import com.vinips.algafood.domain.model.dto.VendaDiaria;
import com.vinips.algafood.domain.service.VendaQueryService;
import com.vinips.algafood.domain.service.VendaReportService;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi{

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public EstatisticasDTO estatisticas() {
		var estatisticasDTO = new EstatisticasDTO();
		
		estatisticasDTO.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
		
		return estatisticasDTO;
	}

	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
		return vendaQueryService.consultarVendasDiarias(filter, timeOffSet);
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
		
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filter, timeOffSet);
		
		//Esse header "attachment" é para informar que esse conteúdo é para ser baixado pelo cliente ao invés de exibido.
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}

}
