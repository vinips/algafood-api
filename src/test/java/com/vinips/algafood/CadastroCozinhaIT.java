package com.vinips.algafood;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
//Esse webEnvironment serve para o TomCat ser levantado e definimos a porta na variável port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//Serve para indicar qual arquivo .properties quer usar, se não usar a anotação ele usa a padrão.
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

	@LocalServerPort
	private int port;

	@Autowired
	private Flyway flyway;

	// Essa anotação Before faz com que esse método seja executado sempre antes de
	// cada método anotado com @Test rodar
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		// Serve para dar um callback do flyway e antes de cada método @Test executar,
		// vai chamar o arquivo afterMigrate.sql e resetando o banco para o estado
		// original. Só funciona se o application.properties que a classe test ta usando
		// estiver com o callback configurado.
		flyway.migrate();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		RestAssured.given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveConter4Cozinhas_QuandoConsultarCozinhas() {
		RestAssured.given().accept(ContentType.JSON).when().get().then().body("", Matchers.hasSize(9));
	}

	@Test
	public void testRetornarStatus201_QuandoCadastrarCozinha() {
		RestAssured.given().body("{ \"nome\": \"Chinesa\" }").contentType(ContentType.JSON).accept(ContentType.JSON)
				.when().post().then().statusCode(HttpStatus.CREATED.value());

	}

	// TESTES DE INTEGRAÇÃO

//	@Autowired
//	private CadastroCozinhaService zcadastroCozinhaService;
//
//	@Test
//	public void testarCadastroCozinhaComSucesso() {
//		// 3 passos de todo teste. Cenário - Ação - Validação.
//		// Cenário
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setNome("Chinesa");
//
//		// Ação
//		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
//
//		// Validação
//		assertThat(novaCozinha).isNotNull();
//		assertThat(novaCozinha.getId()).isNotNull();
//	}
//
//	@Test
//	public void testarCadastroCozinhaSemNome() {
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setNome(null);
//
//		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
//			cadastroCozinhaService.salvar(novaCozinha);
//		});
//
//		assertThat(erroEsperado).isNotNull();
//	}
//
//	@Test
//	public void testarExcluirCozinhaEmUso() {
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setId(1L);
//		
//		EntidadeEmUsoException erroEsperado = Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
//			cadastroCozinhaService.excluir(novaCozinha.getId());
//		});
//		
//		assertThat(erroEsperado).isNotNull();
//		
//	}
//
//	@Test
//	public void testarExcluirCozinhaInexistente() {
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setId(1000L);
//		
//		CozinhaNaoEncontradaException erroEsperado = Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
//			cadastroCozinhaService.excluir(novaCozinha.getId());
//		});
//		
//		assertThat(erroEsperado).isNotNull();
//		
//	}
}
