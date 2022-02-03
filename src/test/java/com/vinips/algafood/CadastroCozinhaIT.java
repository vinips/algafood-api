package com.vinips.algafood;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
//Esse webEnvironment serve para o TomCat ser levantado e definimos a porta na variavel port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT {
	
	@LocalServerPort
	private int port;

	@Test
	public void testarResultado200ConsultaCozinha() {
		RestAssured.given()
			.basePath("/cozinhas")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	
	//TESTES DE INTEGRAÇÃO
	
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
