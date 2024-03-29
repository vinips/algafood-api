package com.vinips.algafood.api.v1.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.vinips.algafood.api.v1.model.assembler.FormaPagamentoDTOAssembler;
import com.vinips.algafood.api.v1.model.disassembler.FormaPagamentoInputDisassembler;
import com.vinips.algafood.api.v1.model.dto.FormaPagamentoDTO;
import com.vinips.algafood.api.v1.model.input.FormaPagamentoInput;
import com.vinips.algafood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.vinips.algafood.domain.model.FormaPagamento;
import com.vinips.algafood.domain.repository.FormaPagamentoRepository;
import com.vinips.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(path = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi{

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoAssembler;

	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoDisassembler;

	//Esse código é um exemplo de como colocar cache com etag.
	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request) {
		//Desabilitamos o shallowEtag para implementar o DeepEtag.
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
		
		if (dataUltimaAtualizacao != null ) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		if (request.checkNotModified(eTag)) {
			return null;
		}
		
		
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();

		if (formasPagamento != null && !formasPagamento.isEmpty()) {
			return ResponseEntity.ok()
					//Seta um cache de 10 segundos na requisição.
					.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
					.eTag(eTag)
					.body(formaPagamentoAssembler.toCollectionModel(formasPagamento));
		}

		return ResponseEntity.noContent().build();

	}

	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long formaPagamentoId) {
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		FormaPagamentoDTO formaPagamentoDTO = formaPagamentoAssembler.toModel(formaPagamento);
		
		return ResponseEntity.ok()
				//Seta um cache de 10 segundos na requisição.
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formaPagamentoDTO);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@Valid @RequestBody FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = formaPagamentoDisassembler.toDomainModel(formaPagamentoInput);

		return formaPagamentoAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamento));
	}

	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
			@Valid @RequestBody FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

		formaPagamentoDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

		return formaPagamentoAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamentoAtual));
	}

	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamento.excluir(formaPagamentoId);
	}

}
