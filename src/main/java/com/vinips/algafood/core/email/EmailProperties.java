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
	
	private EmailImplType emailImplType = EmailImplType.FAKE;
	
	public enum EmailImplType {
		REAL, FAKE;
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
	
}
