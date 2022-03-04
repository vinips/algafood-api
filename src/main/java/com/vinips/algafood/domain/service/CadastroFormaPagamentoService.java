package com.vinips.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinips.algafood.domain.exception.EntidadeEmUsoException;
import com.vinips.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.vinips.algafood.domain.model.FormaPagamento;
import com.vinips.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {
	
	private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de Pagamento de código %d não pode ser removida pois está em uso";

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	//Boa prática anotar os métodos que fazem manipulação de dados com o @Transactional para não ocorrer erros
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	//Aqui ele vai retornar uma Forma de Pagamento
	//Caso não encontre a Forma de Pagamento, vai lançar uma Exception.
	public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId).orElseThrow(
				() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
	}

	@Transactional
	public void excluir(Long formaPagamentoId) {
		try {
			formaPagamentoRepository.deleteById(formaPagamentoId);

			//Estamos forçando o JPA a fazer o flush da transação para que se de erro, ele caia na nossa Exception.
			//Caso contrário ele vai dar o flush só quando a transação fechar, que nesse caso é no fim do nosso método excluir.
			formaPagamentoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
		}

	}

}
