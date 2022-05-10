package com.vinips.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinips.algafood.domain.model.FotoProduto;
import com.vinips.algafood.domain.repository.ProdutoRepository;
import com.vinips.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorage;

	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream inputStream) {
		
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();

		Optional<FotoProduto> fotoExistente = produtoRepository
				.findFotoById(restauranteId, produtoId);

		if(fotoExistente.isPresent()) {
			produtoRepository.delete(fotoExistente.get());
		}
	
		String novoNomeArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
		foto.setNomeArquivo(novoNomeArquivo);
		
		//Melhor fazer a transação de merge no Banco antes de salvar a foto em disco.
		//Pois caso de erro e aconteça rollback, o código lança o erro e não salva a foto em disco.
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = new NovaFoto(novoNomeArquivo, inputStream);
		fotoStorage.armazenar(novaFoto);
		
		return foto;
	}

}
