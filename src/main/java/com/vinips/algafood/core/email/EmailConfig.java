package com.vinips.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vinips.algafood.domain.service.EnvioEmailService;
import com.vinips.algafood.infrastructure.service.email.FakeEnvioEmailService;
import com.vinips.algafood.infrastructure.service.email.SandboxEnvioEmailService;
import com.vinips.algafood.infrastructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;
	
	//Cria um Bean dependendo do tipo que ele for setado no application.properties
	@Bean
	public EnvioEmailService envioEmailService() {
		
		switch(emailProperties.getEmailImplType()) {
		case FAKE:
			return new FakeEnvioEmailService();
		case SMTP:
			return new SmtpEnvioEmailService();
		case SANDBOX:
			return new SandboxEnvioEmailService();
		default:
			return null;
		}
	}
	
}
