package com.vinips.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.model.assembler.RestauranteDTOAssembler;
import com.vinips.algafood.api.model.disassembler.RestauranteInputDisassembler;
import com.vinips.algafood.api.model.dto.RestauranteDTO;
import com.vinips.algafood.api.model.input.RestauranteInput;
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
	
	@Autowired
	private RestauranteDTOAssembler restauranteAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteDisassembler;
	
	@GetMapping
	public ResponseEntity<List<RestauranteDTO>> listar() {
		List<Restaurante> restaurantes = restauranteRepository.findAll();

		if (restaurantes != null && !restaurantes.isEmpty()) {
			return ResponseEntity.ok(restauranteAssembler.toCollectionDTO(restaurantes));
		}

		return ResponseEntity.noContent().build();

	}

	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		return restauranteAssembler.toDTO(restaurante);

//		if (restaurante.isPresent()) {
//			return ResponseEntity.ok(restaurante.get());
//		}
//
//		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@Valid @RequestBody RestauranteInput restauranteInput) {
		try {
			Restaurante restaurante = restauranteDisassembler.toDomainModel(restauranteInput);
			
			return restauranteAssembler.toDTO(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId, @Valid @RequestBody RestauranteInput restauranteInput) {
		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
			
			restauranteDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

			
			//Jeito antigo
//			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
//					"produtos");

			return restauranteAssembler.toDTO(cadastroRestaurante.salvar(restauranteAtual));
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
	
	/*
	//MÉTODOS A SEGUIR SÃO APENAS PARA FINS DE ESTUDO, NÃO UTILIZAMOS.
	  
	@Autowired
	private SmartValidator validator;
	  
	//@PatchMapping("/{restauranteId}")
	public RestauranteDTO atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos,
			HttpServletRequest request) {

		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

		merge(campos, restauranteAtual, request);
		
		validate(restauranteAtual, "restaurante");

		return atualizar(restauranteId, restauranteAtual);
	}

	//Método ficou apenas como exemplo, para fins de estudo
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
		// Usado para enviar como 3º argumento no construtor do
		// HttpMessageNotReadableException
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

		try {
			ObjectMapper objectMapper = new ObjectMapper();

			// No Application.properties setamos para o programa falhar quando recebermos
			// atributos pelo json que setamos
			// para serem ignorados, como o @JsonIgnore na entidade. Normalmente não
			// precisamos setar na mão para ele falhar
			// como fizemos aqui na linha de baixo, porém estamos usando ObjectMapper aqui e
			// se não fizermos ele ignora e não acusa falha.
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);

			// Não é necessário, diferente do Ignored, o ObjectMapper consegue falhar com
			// propriedades desconhecidas.
			// Estamos colocando apenas por segurança, palavras do próprio instrutor do
			// curso, Thiago.
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

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
		} catch (IllegalArgumentException e) {

			// ExceptionUtils é da dependency org.apache.commons no POM
			Throwable rootCause = ExceptionUtils.getRootCause(e);

			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
	
	private void validate(Restaurante restaurante, String objectName) {
		//BindingResult armazena as violações de constraints de validação.
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);

		validator.validate(restaurante, bindingResult);
		
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
		
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
	
	*/

}
