package com.vinips.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.OAS_30)
				.select()
					//Seleciona todos os Controladores
					//.apis(RequestHandlerSelectors.any())
					.apis(RequestHandlerSelectors.basePackage("com.vinips.algafood.api"))
					.build()
				.apiInfo(apiInfo());
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurante")
				.version("1.0.0")
				.contact(new Contact("Vinips", "https://github.com/vinips", "contato@vinips.com"))
				.build();
	}

	
}
