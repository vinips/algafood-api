package com.vinips.algafood.core.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Classe de para configuração do CORs globalmente
//Caso não configurarmos ele globalmente, precisamos usar o @CrossOrigin em cada Controller separadamente.
@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// /** habilita para tudo no mapping e o allowedMethods é para habilitar os métodos (post, get, etc)
		registry.addMapping("/**").allowedMethods("*");
	}
	
}
