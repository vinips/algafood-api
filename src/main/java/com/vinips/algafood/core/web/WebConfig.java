package com.vinips.algafood.core.web;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Classe de para configuração do CORs globalmente
//Caso não configurarmos ele globalmente, precisamos usar o @CrossOrigin em cada Controller separadamente.
@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Autowired
	private ApiDeprecationHandler apiDeprecationHandler;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// /** habilita para tudo no mapping e o allowedMethods é para habilitar os métodos (post, get, etc)
		registry.addMapping("/**").allowedMethods("*");
	}
	
	//Coloca um Etag no Header da requisição.
	//O Etag serve como uma marcação para aquela requisição ficar no cache, quando o cache vence
	//e fazemos uma nova requisição, ele pergunta pro servidor se aquela resposta contendo aquele ETAG teve modificação
	//Caso não tenha, ele continua usando o mesmo do cache e recebe um 304. Mais informações ver aula 17.5
	@Bean
	public Filter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}
	
	
	//Explicação na classe ApiDeprecationHandler
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiDeprecationHandler);
	}
	
}
