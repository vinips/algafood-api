package com.vinips.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

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
import com.vinips.algafood.domain.model.Restaurante;
import com.vinips.algafood.domain.repository.CozinhaRepository;
import com.vinips.algafood.domain.repository.RestauranteRepository;
import com.vinips.algafood.util.DatabaseCleaner;
import com.vinips.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
//Esse webEnvironment serve para o TomCat ser levantado e definimos a porta na variável port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//Serve para indicar qual arquivo .properties quer usar, se não usar a anotação ele usa a padrão.
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

	private static final int RESTAURANTE_ID_INEXISTENTE = 10000;
	private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio.";
    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
	
	@LocalServerPort
	private int port;
	
//	@Autowired
//	private Flyway flyway;
	
	@Autowired
	private DatabaseCleaner dbCleaner;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private int quantidadeRestaurantesCadastrados;
	private String jsonNovoRestauranteTest;
	private String jsonNovoRestauranteCozinhaInexistenteTest;
	private String jsonNovoRestauranteSemCozinhaTest;
	private String jsonNovoRestauranteSemFreteTest;
	
	private Restaurante restauranteJapones;

	// Essa anotação Before faz com que esse método seja executado sempre antes de
	// cada método anotado com @Test rodar
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		jsonNovoRestauranteTest = ResourceUtils.getContentFromResource("/json/correto/novoRestauranteTest.json");
		
		jsonNovoRestauranteCozinhaInexistenteTest = ResourceUtils.getContentFromResource("/json/incorreto/novoRestauranteCozinhaInexistenteTest.json");
		
		jsonNovoRestauranteSemCozinhaTest = ResourceUtils.getContentFromResource("/json/incorreto/novoRestauranteSemCozinhaTest.json");
		
		jsonNovoRestauranteSemFreteTest = ResourceUtils.getContentFromResource("/json/incorreto/novoRestauranteSemFreteTest.json");
		
		// Serve para dar um callback do flyway e antes de cada método @Test executar,
		// vai chamar o arquivo afterMigrate.sql e resetando o banco para o estado
		// original. Só funciona se o application.properties que a classe test ta usando
		// estiver com o callback configurado.
		//flyway.migrate();
		
		dbCleaner.clearTables();

		this.prepararDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveConterMesmaQuantidadeRestaurantesCadastradas_QuandoConsultarRestaurantes() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(quantidadeRestaurantesCadastrados));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
		RestAssured.given()
			.body(jsonNovoRestauranteTest)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then().
			statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostasEStatusCorretos_QuandoConsultarRestauranteExistente() {
		RestAssured.given()
			.pathParam("restauranteId", restauranteJapones.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(restauranteJapones.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
		RestAssured.given()
			.pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastraRestauranteComCozinhaInexistente() {
		RestAssured.given()
			.body(jsonNovoRestauranteCozinhaInexistenteTest)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastraRestauranteSemCozinha() {
		RestAssured.given()
			.body(jsonNovoRestauranteSemCozinhaTest)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastraRestauranteSemFrete() {
		RestAssured.given()
			.body(jsonNovoRestauranteSemFreteTest)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}
	
	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);
		
		System.out.println("Cozinha 1: " + cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Japonesa");
		cozinhaRepository.save(cozinha2);
		
		System.out.println("Cozinha 2: " + cozinha2);
		
		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Restaurante Tailandes");
		restaurante1.setTaxaFrete(new BigDecimal(15));
		restaurante1.setCozinha(cozinha1);
		restauranteRepository.save(restaurante1);
		
		System.out.println("Restaurante 1: " + restaurante1);
		
		restauranteJapones = new Restaurante();
		restauranteJapones.setNome("Restaurante Japonês");
		restauranteJapones.setTaxaFrete(new BigDecimal(20));
		restauranteJapones.setCozinha(cozinha2);
		restauranteRepository.save(restauranteJapones);

		System.out.println("Restaurante 2: " + restauranteJapones);
		
		quantidadeRestaurantesCadastrados = (int) restauranteRepository.count();
		
	}

}
