package com.vinips.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vinips.algafood.core.email.EmailProperties.EmailImplType;
import com.vinips.algafood.domain.service.EnvioEmailService;
import com.vinips.algafood.infrastructure.service.email.FakeEnvioEmailService;
import com.vinips.algafood.infrastructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;
	
	//Cria um Bean dependendo do tipo que ele for setado no application.properties
	@Bean
	public EnvioEmailService envioEmailService() {
		if(EmailImplType.FAKE.equals(emailProperties.getEmailImplType())){
			return new FakeEnvioEmailService();
		} else {
			return new SmtpEnvioEmailService();
		}
	}
	
}
