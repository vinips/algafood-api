package com.vinips.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

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

import com.vinips.algafood.domain.model.Cozinha;
import com.vinips.algafood.domain.repository.CozinhaRepository;
import com.vinips.algafood.util.DatabaseCleaner;
import com.vinips.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
//Esse webEnvironment serve para o TomCat ser levantado e definimos a porta na variável port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//Serve para indicar qual arquivo .properties quer usar, se não usar a anotação ele usa a padrão.
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

	private static final int COZINHA_ID_INEXISTENTE = 10000;
	
	private int quantidadeCozinhasCadastradas;
	private String jsonNovaCozinhaTest;
	private Cozinha cozinhaAmericana;
	
	@LocalServerPort
	private int port;
	
//	@Autowired
//	private Flyway flyway;
	
	@Autowired
	private DatabaseCleaner dbCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	// Essa anotação Before faz com que esse método seja executado sempre antes de
	// cada método anotado com @Test rodar
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		jsonNovaCozinhaTest = ResourceUtils.getContentFromResource("/json/correto/novaCozinhaTest.json");

		// Serve para dar um callback do flyway e antes de cada método @Test executar,
		// vai chamar o arquivo afterMigrate.sql e resetando o banco para o estado
		// original. Só funciona se o application.properties que a classe test ta usando
		// estiver com o callback configurado.
		//flyway.migrate();
		
		dbCleaner.clearTables();

		this.prepararDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveConterMesmaQuantidadeCozinhasCadastradas_QuandoConsultarCozinhas() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(quantidadeCozinhasCadastradas));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		RestAssured.given()
			.body(jsonNovaCozinhaTest)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then().
			statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostasEStatusCorretos_QuandoConsultarCozinhaExistente() {
		RestAssured.given()
		.pathParam("cozinhaId", cozinhaAmericana.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cozinhaAmericana.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		RestAssured.given()
		.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);
		
		cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cozinhaRepository.save(cozinhaAmericana);
		
		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
		
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
