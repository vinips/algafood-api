package com.vinips.algafood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinips.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.vinips.algafood.domain.exception.NegocioException;
import com.vinips.algafood.domain.model.Restaurante;
import com.vinips.algafood.domain.repository.RestauranteRepository;
import com.vinips.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		
		if (restaurantes != null && !restaurantes.isEmpty()) {
			return ResponseEntity.ok(restaurantes);
		}

		return ResponseEntity.noContent().build();

	}

	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId) {
		
		//Jeito simplificado
		return cadastroRestaurante.buscarOuFalhar(restauranteId);

//		if (restaurante.isPresent()) {
//			return ResponseEntity.ok(restaurante.get());
//		}
//
//		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		try {
			return cadastroRestaurante.salvar(restaurante);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		try {
			// Jeito simplificado
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
			
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco",
					"dataCadastro", "produtos");
			
			return cadastroRestaurante.salvar(restauranteAtual);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
			
		// Jeito antigo
//		if (restauranteAtual.isPresent()) {
//			BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id", "formasPagamento", "endereco",
//					"dataCadastro", "produtos");
//			Restaurante restauranteSalvo = cadastroRestaurante.salvar(restauranteAtual.get());
//			return ResponseEntity.ok(restauranteSalvo);
//		}
//
//		return ResponseEntity.notFound().build();
//
//	} catch (EntidadeNaoEncontradaException e) {
//		return ResponseEntity.badRequest().body(e.getMessage());
//	}
	}

	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId) {
		cadastroRestaurante.excluir(restauranteId);

	}

	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> campos) {

		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

		merge(campos, restauranteAtual);

		return atualizar(restauranteId, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();

		// Pega os dados de Origem e cria uma instancia de Restaurante com esses dados.
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			// Pega o campo passado e ve se ele bate com algum atributo de Restaurante, se
			// sim cria o Campo.
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			// Necessário para habilitar os campos privados do encapsulamento.
			field.setAccessible(true);

			// Converte o valor passado para o seu tipo certo, por Exemplo se passou inteiro
			// e espera BigDecimal, converte o inteiro em BigDecimal
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

			ReflectionUtils.setField(field, restauranteDestino, novoValor);

			// System.out.println(nomePropriedade + " = " + valorPropriedade + " = " +
			// novoValor);
		});
	}

	@GetMapping("/taxa-por-frete")
	public List<Restaurante> restaurantePorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}

	@GetMapping("/nome-cozinha-id")
	public List<Restaurante> restaurantePorNomeCozinhaId(String nome, Long id) {
		return restauranteRepository.consultarPorNome(nome, id);
	}

	@GetMapping("/primeiro-por-nome")
	public Optional<Restaurante> restaurantePrimeiroPorNome(String nome) {
		return restauranteRepository.findFirstByNomeContaining(nome);
	}

	@GetMapping("/top2-por-nome")
	public List<Restaurante> restauranteTop2PorNome(String nome) {
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}

	@GetMapping("/find")
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}

	@GetMapping("/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome) {
		return restauranteRepository.findComFreteGratis(nome);
	}

	@GetMapping("/primeiro")
	public Optional<Restaurante> restaurantePrimeiro() {
		return restauranteRepository.buscarPrimeiro();
	}

}