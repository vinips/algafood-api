package com.vinips.algafood.core.web;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

// Fonte: https://github.com/spring-projects/spring-hateoas/issues/263#issuecomment-358969098
// Implementado na aula 20.10
// Essa classe serve para que nossos MediaTypes customizados sejam no padrão HAL, que é o mesmo que o HATEOAS utiliza quando usamos o MediaTypes.application_json.
// Se estiver utilizando Spring Boot 2.5.6 +, utilizar a classe comentada abaixo dessa
@Component
public class HalCustomMediaTypeEnabler {
	
    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Autowired
    public HalCustomMediaTypeEnabler(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
    }

    @PostConstruct
    public void enableVndHalJson() {
        for (HttpMessageConverter<?> converter : requestMappingHandlerAdapter.getMessageConverters()) {
            if (converter instanceof MappingJackson2HttpMessageConverter 
            		&& converter.getSupportedMediaTypes().contains(MediaTypes.HAL_JSON)) {
                
            	MappingJackson2HttpMessageConverter messageConverter = (MappingJackson2HttpMessageConverter) converter;
                messageConverter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON, 
                		AlgaMediaTypes.V1_APPLICATION_JSON, AlgaMediaTypes.V2_APPLICATION_JSON));
            }
        }
    }
    
}

// Na Versão 2.5.6 do Spring Boot, não precisamos mais da classe HalCustomMediaTypeEnabler, podemos utilizar essa abaixo
// Pois a o HATEOAS tem uma classe de confinguração para o Hal que implementa um método de MediaType customizado. 
// Na versão 1.0.2.RELEASE que usamos atualmente (pois é essa versão do HATEOAS que vem com a versão 2.2.2.RELEASE do Spring Boot que estamos usando)
// Esse método não foi implementado ainda.

/*
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.hateoas.mediatype.hal.HalConfiguration; import
 * org.springframework.http.MediaType;
 * 
 * @Configuration public class AlgaHalConfiguration {
 * 
 * @Bean public HalConfiguration globalPolicy() { return new HalConfiguration()
 * .withMediaType(MediaType.APPLICATION_JSON)
 * .withMediaType(AlgaMediaTypes.V1_APPLICATION_JSON); }
 * 
 * }
 */