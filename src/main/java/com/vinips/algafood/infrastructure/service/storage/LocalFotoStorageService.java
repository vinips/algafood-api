package com.vinips.algafood.infrastructure.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.vinips.algafood.domain.service.FotoStorageService;
import com.vinips.algafood.infrastructure.exception.StorageException;

@Service
public class LocalFotoStorageService implements FotoStorageService {
	
	@Value("${algafood.storage.local.diretorio-fotos}")
	private String diretorioFotos;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
			
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (IOException e) {
			throw new StorageException("Não foi possível armazenar o arquivo", e);
		}
	}
	
	@Override
	public void remover(String nomeArquivo) {
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);
			
			Files.deleteIfExists(arquivoPath);
		} catch (IOException e) {
			throw new StorageException("Não foi possível excluir o arquivo", e);
		}
		
	}
	
	@Override
	public InputStream recuperar(String nomeArquivo) {
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);
			
			return Files.newInputStream(arquivoPath);
		} catch (IOException e) {
			throw new StorageException("Não foi possível recuperar o arquivo", e);
		}

	}
	
	//Retornando um Path completo com URL + nome do Arquivo
	private Path getArquivoPath(String nomeArquivo) {
		return Path.of(diretorioFotos, nomeArquivo);
	}
	
}
