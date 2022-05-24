package com.vinips.algafood.core.email;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

	@NotBlank
	private String remetente;
	
	private Sandbox sandbox = new Sandbox();
	
	private EmailImplType emailImplType = EmailImplType.FAKE;
	
	public class Sandbox {
		
		private String destinatario;

		public String getDestinatario() {
			return destinatario;
		}

		public void setDestinatario(String destinatario) {
			this.destinatario = destinatario;
		}
		
	}
	
	public enum EmailImplType {
		FAKE, SMTP, SANDBOX;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public EmailImplType getEmailImplType() {
		return emailImplType;
	}

	public void setEmailImplType(EmailImplType emailImplType) {
		this.emailImplType = emailImplType;
	}

	public Sandbox getSandbox() {
		return sandbox;
	}

	public void setSandbox(Sandbox sandbox) {
		this.sandbox = sandbox;
	}
	
}
