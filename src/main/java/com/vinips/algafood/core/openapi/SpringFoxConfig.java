package com.vinips.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vinips.algafood.api.exceptionhandler.Problem;
import com.vinips.algafood.api.v1.model.dto.CidadeDTO;
import com.vinips.algafood.api.v1.model.dto.CozinhaDTO;
import com.vinips.algafood.api.v1.model.dto.EstadoDTO;
import com.vinips.algafood.api.v1.model.dto.FormaPagamentoDTO;
import com.vinips.algafood.api.v1.model.dto.GrupoDTO;
import com.vinips.algafood.api.v1.model.dto.PedidoResumoDTO;
import com.vinips.algafood.api.v1.model.dto.PermissaoDTO;
import com.vinips.algafood.api.v1.model.dto.ProdutoDTO;
import com.vinips.algafood.api.v1.model.dto.RestauranteResumoDTO;
import com.vinips.algafood.api.v1.model.dto.UsuarioDTO;
import com.vinips.algafood.api.v1.openapi.dto.CidadesDTOOpenApi;
import com.vinips.algafood.api.v1.openapi.dto.CozinhasDTOOpenApi;
import com.vinips.algafood.api.v1.openapi.dto.EstadosDTOOpenApi;
import com.vinips.algafood.api.v1.openapi.dto.FormasPagamentoDTOOpenApi;
import com.vinips.algafood.api.v1.openapi.dto.GruposDTOOpenApi;
import com.vinips.algafood.api.v1.openapi.dto.LinksDTOOpenApi;
import com.vinips.algafood.api.v1.openapi.dto.PageableDTOOpenApi;
import com.vinips.algafood.api.v1.openapi.dto.PedidosResumoDTOOpenApi;
import com.vinips.algafood.api.v1.openapi.dto.PermissoesDTOOpenApi;
import com.vinips.algafood.api.v1.openapi.dto.ProdutosDTOOpenApi;
import com.vinips.algafood.api.v1.openapi.dto.RestaurantesResumoDTOOpenApi;
import com.vinips.algafood.api.v1.openapi.dto.UsuariosDTOOpenApi;
import com.vinips.algafood.api.v2.model.dto.CidadeDTOV2;
import com.vinips.algafood.api.v2.model.dto.CozinhaDTOV2;
import com.vinips.algafood.api.v2.openapi.dto.CidadesDTOOpenApiV2;
import com.vinips.algafood.api.v2.openapi.dto.CozinhasDTOOpenApiV2;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
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
	
	//TODO https://springdoc.org/#migrating-from-springfox Link para migração do SpringFox para Springdoc, que é mais atualizado atualmente
	
	//Bean para fazer com que o SpringFox carregue o módulo de conversão de datas.
	//Com esse bean gerenciado pelo Springboot, conseguimos Serializar a classe OffsetDateTime quando utilizamos SpringFox.
	//Qualquer dúvida olhar aula 18.14
	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}
	
	@Bean
	public Docket apiDocketV1() {
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30)
				.groupName("V1")
				.select()
					//Seleciona todos os Controladores
					//.apis(RequestHandlerSelectors.any())
					.apis(RequestHandlerSelectors.basePackage("com.vinips.algafood.api"))
					.paths(PathSelectors.ant("/v1/**"))
					.build()
				.useDefaultResponseMessages(false)//Desabilita os codigos de Status que por padrão o SpringFox coloca na documentação
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				//Aqui eu ignoro os parametros que não quero que a documentação apresente, como por exemplo esse ServletWebRequest que usamos no método listar do FormaPagamentoController
				.ignoredParameterTypes(ServletWebRequest.class)
				//Com isso nós adicionamos um modelo extra que é o Problem para ser listado no SpringFox
				.additionalModels(typeResolver.resolve(Problem.class))
				//Para fins de documentação, nós fazemos a substituição da interface Pageable do org.springframework.data.domain pela nossa customizada. Aula 18.20
				.directModelSubstitute(Pageable.class, PageableDTOOpenApi.class)
				//Aula 19.39
				.directModelSubstitute(Links.class, LinksDTOOpenApi.class)
				//Aqui nós substituimos um Page<CozinhaDTO> por um CozinhasDtoOpenApi que dentro tem uma lista de CozinhaDTO e outros valores do Page (size, totalElements, totalPages, number). Aula 18.21
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CozinhaDTO.class), 
						CozinhasDTOOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, PedidoResumoDTO.class), 
						PedidosResumoDTOOpenApi.class))
				
				//Aula 19.40
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeDTO.class), 
						CidadesDTOOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, EstadoDTO.class), 
						EstadosDTOOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, FormaPagamentoDTO.class), 
						FormasPagamentoDTOOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, GrupoDTO.class), 
						GruposDTOOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, PermissaoDTO.class), 
						PermissoesDTOOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, ProdutoDTO.class), 
						ProdutosDTOOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, RestauranteResumoDTO.class), 
						RestaurantesResumoDTOOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, UsuarioDTO.class), 
						UsuariosDTOOpenApi.class))
				
				.apiInfo(apiInfoV1())
				.tags(new Tag("Cidades", "Gerencia as cidades"), 
						new Tag("Cozinhas", "Gerencia as cozinhas"),
						new Tag("Grupos", "Gerencia os grupos"),
						new Tag("Formas de Pagamento", "Gerencia as formas de pagamento"),
						new Tag("Pedidos", "Gerencia os pedidos"),
						new Tag("Restaurantes", "Gerencia os restaurantes"),
						new Tag("Estados", "Gerencia os estados"),
						new Tag("Produtos", "Gerencia os produtos"),
						new Tag("Usuários", "Gerencia os usuarios"),
						new Tag("Estatísticas", "Gerencia as estatísticas"),
						new Tag("Permissões", "Gerencia as permissões"));
				//Usamos o Squiggly para adicionar campos de pesquisa nas requisições. No PedidoControler por exemplo temos o parametro "campos" que falamos os campos que queremos receber na pesquisa.
				//Essa configuração faz com que isso seja mapeado pelo SpringFox. Sem essa configuração, ele não aparece na documentação. Aula 18.25
				//Na aula 18.26 nós fazemos essa configuração apenas nos locais que iremos utilizar, pois se deixarmos aqui ele irá aparecer até mesmo para os endpoints que não usam Squiggly.
//				.globalRequestParameters(Collections.singletonList(
//			            new RequestParameterBuilder()
//			                    .name("campos")
//			                    .description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//			                    .in(ParameterType.QUERY)
//			                    .required(true)
//			                    .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//			                    .build())
//			    )
	}
	
	@Bean
	public Docket apiDocketV2() {
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30)
				.groupName("V2")
				.select()
					//Seleciona todos os Controladores
					//.apis(RequestHandlerSelectors.any())
					.apis(RequestHandlerSelectors.basePackage("com.vinips.algafood.api"))
					.paths(PathSelectors.ant("/v2/**"))
					.build()
				.useDefaultResponseMessages(false)//Desabilita os codigos de Status que por padrão o SpringFox coloca na documentação
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				//Aqui eu ignoro os parametros que não quero que a documentação apresente, como por exemplo esse ServletWebRequest que usamos no método listar do FormaPagamentoController
				.ignoredParameterTypes(ServletWebRequest.class)
				//Com isso nós adicionamos um modelo extra que é o Problem para ser listado no SpringFox
				.additionalModels(typeResolver.resolve(Problem.class))
				//Para fins de documentação, nós fazemos a substituição da interface Pageable do org.springframework.data.domain pela nossa customizada. Aula 18.20
				.directModelSubstitute(Pageable.class, PageableDTOOpenApi.class)
				.directModelSubstitute(Links.class, LinksDTOOpenApi.class)
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CozinhaDTOV2.class),
						CozinhasDTOOpenApiV2.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeDTOV2.class),
						CidadesDTOOpenApiV2.class))
				
				.apiInfo(apiInfoV2())
						
				.tags(new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Cozinhas", "Gerencia as cozinhas"));
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
	
	private ApiInfo apiInfoV1() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurante")
				.version("1.0.0")
				.contact(new Contact("Vinips", "https://github.com/vinips", "contato@vinips.com"))
				.build();
	}
	
	private ApiInfo apiInfoV2() {
		return new ApiInfoBuilder()
				.title("AlgaFood API (Depreciada)")
				.description("API aberta para clientes e restaurante <br>"
						+ "<strong>Essa versão da API foi feita para fins de estudo e não deve ser utilizada. Utilize a versão 1")
				.version("2.0.0")
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
