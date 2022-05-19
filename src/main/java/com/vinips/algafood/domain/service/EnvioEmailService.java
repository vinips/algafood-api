package com.vinips.algafood.domain.service;

import java.util.Set;

public interface EnvioEmailService {

	void enviar(Mensagem mensagem);
	
	class Mensagem {
		
		private Set<String> destinatarios;
		private String assunto;
		private String texto;
		
		public Mensagem(Set<String> destinatarios, String assunto, String texto) {
			super();
			this.destinatarios = destinatarios;
			this.assunto = assunto;
			this.texto = texto;
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
		
	}
	
	
}
