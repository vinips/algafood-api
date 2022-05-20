package com.vinips.algafood.domain.service;

import java.util.Map;
import java.util.Set;

public interface EnvioEmailService {

	void enviar(Mensagem mensagem);
	
	class Mensagem {
		
		private Set<String> destinatarios;
		private String assunto;
		private String texto;
		private Map<String, Object> variaveis;
		
		public Mensagem(Set<String> destinatarios, String assunto, String texto, Map<String, Object> variaveis) {
			super();
			this.destinatarios = destinatarios;
			this.assunto = assunto;
			this.texto = texto;
			this.variaveis = variaveis;
		}

		public Set<String> getDestinatarios() {
			return destinatarios;
		}

		public void setDestinatarios(Set<String> destinatarios) {
			this.destinatarios = destinatarios;
		}

		public String getAssunto() {
			return assunto;
		}

		public void setAssunto(String assunto) {
			this.assunto = assunto;
		}

		public String getTexto() {
			return texto;
		}

		public void setTexto(String texto) {
			this.texto = texto;
		}

		public Map<String, Object> getVariaveis() {
			return variaveis;
		}

		public void setVariaveis(Map<String, Object> variaveis) {
			this.variaveis = variaveis;
		}
		
	}
	
	
}
