package com.vinips.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@Import(BeanValidatorPluginsConfiguration.class) Utilizar caso coloque o springfox-bean-validators
public class SpringFoxConfig {
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.OAS_30)
				.select()
					//Seleciona todos os Controladores
					//.apis(RequestHandlerSelectors.any())
					.apis(RequestHandlerSelectors.basePackage("com.vinips.algafood.api"))
					.build()
				.useDefaultResponseMessages(false)//Desabilita os codigos de Status que por padrão o SpringFox coloca na documentação
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades", "Gerencia as cidades"));
	}
	
	private List<Response> globalGetResponseMessages(){
		return Arrays.asList(
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.NOT_ACCEPTABLE))
					.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build(),
					
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.INTERNAL_SERVER_ERROR))
					.description("Erro interno no servidor")
					.build()
			);
	}
	
	private List<Response> globalPostPutResponseMessages(){
		return Arrays.asList(
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.BAD_REQUEST))
					.description("Requisição inválida (erro do cliente)")
					.build(),
					
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.NOT_ACCEPTABLE))
					.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build(),
					
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.UNSUPPORTED_MEDIA_TYPE))
					.description("Requisição recusada porque o corpo está em um formato não suportado")
					.build(),
					
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.INTERNAL_SERVER_ERROR))
					.description("Erro interno no servidor")
					.build()
			);
	}
	
	private List<Response> globalDeleteResponseMessages(){
		return Arrays.asList(
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.BAD_REQUEST))
					.description("Requisição inválida (erro do cliente)")
					.build(),
					
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.INTERNAL_SERVER_ERROR))
					.description("Erro interno no servidor")
					.build()
			);
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurante")
				.version("1.0.0")
				.contact(new Contact("Vinips", "https://github.com/vinips", "contato@vinips.com"))
				.build();
	}
	
	private String httpStatusToString(HttpStatus status) {
		return String.valueOf(status.value());
	}
	
}
