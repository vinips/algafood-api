package com.vinips.algafood.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.vinips.algafood.core.email.EmailProperties;
import com.vinips.algafood.domain.service.EnvioEmailService;
import com.vinips.algafood.infrastructure.exception.EmailException;

public class SmtpEnvioEmailService implements EnvioEmailService	{

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Override
	public void enviar(Mensagem mensagem) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(emailProperties.getRemetente());
			helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			helper.setSubject(mensagem.getAssunto());
			//o true é que vamos enviar no padrão HTML e não no padrão texto
			helper.setText(mensagem.getTexto(), true);
			
			
			//mailSender.send(null);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail", e);
		}
		
		
	}

}
