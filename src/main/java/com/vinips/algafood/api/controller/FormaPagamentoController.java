package com.vinips.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.vinips.algafood.api.model.assembler.FormaPagamentoDTOAssembler;
import com.vinips.algafood.api.model.disassembler.FormaPagamentoInputDisassembler;
import com.vinips.algafood.api.model.dto.FormaPagamentoDTO;
import com.vinips.algafood.api.model.input.FormaPagamentoInput;
import com.vinips.algafood.domain.model.FormaPagamento;
import com.vinips.algafood.domain.repository.FormaPagamentoRepository;
import com.vinips.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoAssembler;

	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoDisassembler;

	@GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> listar() {
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();

		if (formasPagamento != null && !formasPagamento.isEmpty()) {
			return ResponseEntity.ok(formaPagamentoAssembler.toCollectionDTO(formasPagamento));
		}

		return ResponseEntity.noContent().build();

	}

	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO buscar(@PathVariable Long formaPagamentoId) {
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

		return formaPagamentoAssembler.toDTO(formaPagamento);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@Valid @RequestBody FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = formaPagamentoDisassembler.toDomainModel(formaPagamentoInput);

		return formaPagamentoAssembler.toDTO(cadastroFormaPagamento.salvar(formaPagamento));
	}

	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
			@Valid @RequestBody FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

		formaPagamentoDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

		return formaPagamentoAssembler.toDTO(cadastroFormaPagamento.salvar(formaPagamentoAtual));
	}

	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamento.excluir(formaPagamentoId);
	}

}
