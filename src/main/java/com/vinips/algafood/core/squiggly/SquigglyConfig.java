package com.vinips.algafood.core.squiggly;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

@Configuration
public class SquigglyConfig {
	
	//Estamos criando um Bean que devolve um filtro de Servlet.
	//Esse Filtro serve para que na hora da requisição do Cliente, ele passe por params os campos que ele quer retornar.
	@Bean
	public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper){
		//Por padrão o nome do parametro é "fields", caso deseje mudar é só inserir o nome desejado abaixo, ex: "campos"
		//Caso não queira nada, retire os parametros ("campos", null) e use "fields" na requisição http
		Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos", null));
		
		var filterRegistration = new FilterRegistrationBean<SquigglyRequestFilter>();
		filterRegistration.setFilter(new SquigglyRequestFilter());
		filterRegistration.setOrder(1);
		
		//Essa configuração serve para limitarmos quais os end-points podem ter acesso a esse filtro.
		//var urlPatterns = Arrays.asList("/pedidos/*", "/restaurante/*");
		//filterRegistration.setUrlPatterns(urlPatterns);
		
		
		return filterRegistration;
	}

	
}
