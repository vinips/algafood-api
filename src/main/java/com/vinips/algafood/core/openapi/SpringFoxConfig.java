package com.vinips.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vinips.algafood.api.exceptionhandler.Problem;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@Import(BeanValidatorPluginsConfiguration.class) Utilizar caso coloque o springfox-bean-validators
public class SpringFoxConfig {
	
	//Bean para fazer com que o SpringFox carregue o módulo de conversão de datas.
	//Com esse bean gerenciado pelo Springboot, conseguimos Serializar a classe OffsetDateTime quando utilizamos SpringFox.
	//Qualquer dúvida olhar aula 18.14
	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}
	
	@Bean
	public Docket apiDocket() {
		TypeResolver typeResolver = new TypeResolver();
		
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
				//Com isso nós adicionamos um modelo extra que é o Problem para ser listado no SpringFox
				.additionalModels(typeResolver.resolve(Problem.class))
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
					.representation( MediaType.APPLICATION_JSON )
                    .apply(getProblemaModelReference())
					.build()
			);
	}
	
	private List<Response> globalPostPutResponseMessages(){
		return Arrays.asList(
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.BAD_REQUEST))
					.description("Requisição inválida (erro do cliente)")
					.representation( MediaType.APPLICATION_JSON )
                    .apply(getProblemaModelReference())
					.build(),
					
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.NOT_ACCEPTABLE))
					.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build(),
					
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.UNSUPPORTED_MEDIA_TYPE))
					.description("Requisição recusada porque o corpo está em um formato não suportado")
					.representation( MediaType.APPLICATION_JSON )
                    .apply(getProblemaModelReference())
					.build(),
					
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.INTERNAL_SERVER_ERROR))
					.description("Erro interno no servidor")
					.representation( MediaType.APPLICATION_JSON )
                    .apply(getProblemaModelReference())
					.build()
			);
	}
	
	private List<Response> globalDeleteResponseMessages(){
		return Arrays.asList(
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.BAD_REQUEST))
					.description("Requisição inválida (erro do cliente)")
					.representation( MediaType.APPLICATION_JSON )
                    .apply(getProblemaModelReference())
					.build(),
					
				new ResponseBuilder()
					.code(httpStatusToString(HttpStatus.INTERNAL_SERVER_ERROR))
					.description("Erro interno no servidor")
					.representation( MediaType.APPLICATION_JSON )
                    .apply(getProblemaModelReference())
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
	
	//Serve para montar um Problem e apresentar no corpo do Springfox do erro
	private Consumer<RepresentationBuilder> getProblemaModelReference() {
	    return r -> r.model(m -> m.name("Problem")
	            .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
	                    q -> q.name("Problem").namespace("com.vinips.algafood.api.exceptionhandler")))));
	}
	
}
