package com.vinips.algafood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;

import com.vinips.algafood.core.email.EmailProperties;

public class FakeEnvioEmailService extends SmtpEnvioEmailService {
	
	@Autowired
	private EmailProperties emailProperties;

	@Override
	public void enviar(Mensagem mensagem) {
		
		System.out.println("Iniciando envio de e-mail Fake");
		System.out.println("------------------------------");
		System.out.println("Assunto: " + mensagem.getAssunto());
		System.out.println("From: " + emailProperties.getRemetente());
		System.out.println("To: " + mensagem.getDestinatarios().toString());
		System.out.println("                               ");
		System.out.println(processarTemplate(mensagem));
		System.out.println("------------------------------");
		
	}

}
