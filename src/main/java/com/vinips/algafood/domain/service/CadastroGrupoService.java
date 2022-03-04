package com.vinips.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinips.algafood.domain.exception.EntidadeEmUsoException;
import com.vinips.algafood.domain.exception.GrupoNaoEncontradoException;
import com.vinips.algafood.domain.model.Grupo;
import com.vinips.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

	private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido pois está em uso";

	@Autowired
	private GrupoRepository grupoRepository;

	// Boa prática anotar os métodos que fazem manipulação de dados com o
	// @Transactional para não ocorrer erros
	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}

	// Aqui ele vai retornar uma Forma de Pagamento
	// Caso não encontre a Forma de Pagamento, vai lançar uma Exception.
	public Grupo buscarOuFalhar(Long grupoId) {
		return grupoRepository.findById(grupoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}

	@Transactional
	public void excluir(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);

			// Estamos forçando o JPA a fazer o flush da transação para que se de erro, ele
			// caia na nossa Exception.
			// Caso contrário ele vai dar o flush só quando a transação fechar, que nesse
			// caso é no fim do nosso método excluir.
			grupoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
		}

	}

}
