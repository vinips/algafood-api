package com.vinips.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinips.algafood.domain.exception.EntidadeEmUsoException;
import com.vinips.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.vinips.algafood.domain.model.Cidade;
import com.vinips.algafood.domain.model.Cozinha;
import com.vinips.algafood.domain.model.Restaurante;
import com.vinips.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removida pois está em uso";

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;

	//Boa prática anotar os métodos que fazem manipulação de dados com o @Transactional para não ocorrer erros
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);

		return restauranteRepository.save(restaurante);
	}
	
	//Aqui ele vai retornar um Restaurante
	//Caso não encontre a restaurante, vai lançar uma Exception.
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).orElseThrow(
				() -> new RestauranteNaoEncontradoException(restauranteId));
	}

	@Transactional
	public void excluir(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
			
			//Estamos forçando o JPA a fazer o flush da transação para que se de erro, ele caia na nossa Exception.
			//Caso contrário ele vai dar o flush só quando a transação fechar, que nesse caso é no fim do nosso método excluir.
			restauranteRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(restauranteId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}

	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		
		restaurante.ativar();
		
		//Não precisamos chamar o salvar nesse caso pois essa instância de restaurante que
		//o buscarOuFalhar nos devolveu esta sendo gerenciado pelo contexto de persistência do JPA.
		//Então qualquer modificação nele vai ser persistido no banco de dados quando 
		//o Transactional chegar no final e dar o merge automático.
	}
	

	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		
		restaurante.inativar();
		
		//Não precisamos chamar o salvar nesse caso pois essa instância de restaurante que
		//o buscarOuFalhar nos devolveu esta sendo gerenciado pelo contexto de persistência do JPA.
		//Então qualquer modificação nele vai ser persistido no banco de dados quando 
		//o Transactional chegar no final e dar o merge automático.
	}

}
