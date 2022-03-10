package com.vinips.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinips.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.vinips.algafood.domain.model.Produto;
import com.vinips.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	//Boa prática anotar os métodos que fazem manipulação de dados com o @Transactional para não ocorrer erros
	@Transactional
	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public Produto buscarOuFalhar(Long produtoId, Long restauranteId) {
		return produtoRepository.findByIdAndRestauranteId(produtoId, restauranteId).orElseThrow(
				() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));
	}

}
