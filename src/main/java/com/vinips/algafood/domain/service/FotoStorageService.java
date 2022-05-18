package com.vinips.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {
	
	void armazenar(NovaFoto novaFoto);
	
	void remover(String nomeArquivo);
	
	InputStream recuperar(String nomeArquivo);
	
	default void substituir(NovaFoto novaFoto, String nomeArquivoExistente) {
		if(nomeArquivoExistente != null) {
			this.remover(nomeArquivoExistente);
		}
		this.armazenar(novaFoto);
	}
	
	default String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "__" + nomeOriginal;
	}
	
	class NovaFoto {
		
		private String nomeArquivo;
		private String contentType;
		private InputStream inputStream;
		
		public NovaFoto(String nomeArquivo, String contentType, InputStream inputStream) {
			super();
			this.nomeArquivo = nomeArquivo;
			this.contentType = contentType;
			this.inputStream = inputStream;
		}

		public String getNomeArquivo() {
			return nomeArquivo;
		}

		public void setNomeArquivo(String nomeArquivo) {
			this.nomeArquivo = nomeArquivo;
		}


		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public InputStream getInputStream() {
			return inputStream;
		}

		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}
		
	}
	
}
