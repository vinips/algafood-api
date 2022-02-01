package com.vinips.algafood.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

	//Essa classe serve para unir o Bean Validator consiga validar as propriedades do ValidatorMessages.properties
	//dentro do messages.properties, é como se tivessemos juntando os 2 arquivos em um só.
	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		
		return bean;
	}
	
}
