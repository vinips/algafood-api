package com.vinips.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.vinips.algafood.api.model.assembler.RestauranteDTOAssembler;
import com.vinips.algafood.api.model.disassembler.RestauranteInputDisassembler;
import com.vinips.algafood.api.model.dto.RestauranteDTO;
import com.vinips.algafood.api.model.input.RestauranteInput;
import com.vinips.algafood.api.model.view.RestauranteView;
import com.vinips.algafood.api.openapi.controller.RestauranteControllerOpenApi;
import com.vinips.algafood.domain.exception.CidadeNaoEncontradaException;
import com.vinips.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.vinips.algafood.domain.exception.NegocioException;
import com.vinips.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.vinips.algafood.domain.model.Restaurante;
import com.vinips.algafood.domain.repository.RestauranteRepository;
import com.vinips.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController  implements RestauranteControllerOpenApi{

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteDTOAssembler restauranteAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteDisassembler;
	
	//O JsonView serve para enviar pro json apenas o que estiver anotado com ele com aquela propriedade. Aula 13.1
	//Deixamos de usar o @JsonView quando passamos a usar DTO em vez de retornar a entidade em si.
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public List<RestauranteDTO> listar() {
		List<Restaurante> restaurantes = restauranteRepository.findAll();

		return restauranteAssembler.toCollectionDTO(restaurantes);
		
		//Na aula 16.3 aprendemos a setar o CORS manualmente. 
		//Na 16.4 colocamos o CORS para o controlador todo com a anota????o @CrossOrigin
//		return ResponseEntity
//				.ok()
//				.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
//				.body(restauranteModel);
	}
	
	//Colocamos isso pois na documenta????o, o SpringFox trata o m??todo listar e listarApenasNome como o mesmo por ser o mesmo endpoint, e com isso ele oculta 1 deles.
	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteDTO> listarApenasNome() {
		return listar();
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
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
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
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
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
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.ativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getLocalizedMessage(), e);
		}
	}

	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.inativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getLocalizedMessage(), e);
		}
	}
	
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		cadastroRestaurante.abrir(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechar(restauranteId);
	}
	
	/*
	//M??TODOS A SEGUIR S??O APENAS PARA FINS DE ESTUDO, N??O UTILIZAMOS.
	 * 
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam (required = false) String projecao) {
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteDTO> restaurantesDTO = restauranteAssembler.toCollectionDTO(restaurantes);
//		
//		//Essa classe "envelopa" o DTO e com isso conseguimos setar qual Serialization View queremos passar de acordo com o parametro recebido
//		MappingJacksonValue restauranteWrapper = new MappingJacksonValue(restaurantesDTO);
//		
//		restauranteWrapper.setSerializationView(RestauranteView.Resumo.class);
//		
//		if("apenas-nome".equals(projecao)) {
//			restauranteWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		} else if ("completo".equals(projecao)) {
//			restauranteWrapper.setSerializationView(null);
//		}
//		
//		return restauranteWrapper;
//	}
 * 
 	@GetMapping
	public ResponseEntity<List<RestauranteDTO>> listar() {
		List<Restaurante> restaurantes = restauranteRepository.findAll();

		if (restaurantes != null && !restaurantes.isEmpty()) {
			return ResponseEntity.ok(restauranteAssembler.toCollectionDTO(restaurantes));
		}

		return ResponseEntity.noContent().build();

	}
	  
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

	//M??todo ficou apenas como exemplo, para fins de estudo
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
		// Usado para enviar como 3?? argumento no construtor do
		// HttpMessageNotReadableException
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

		try {
			//ObjectMapper ?? uma classe que usamos pra serealizar e deserealizar json em objetos java e vice versa;
			ObjectMapper objectMapper = new ObjectMapper();

			// No Application.properties setamos para o programa falhar quando recebermos
			// atributos pelo json que setamos
			// para serem ignorados, como o @JsonIgnore na entidade. Normalmente n??o
			// precisamos setar na m??o para ele falhar
			// como fizemos aqui na linha de baixo, por??m estamos usando ObjectMapper aqui e
			// se n??o fizermos ele ignora e n??o acusa falha.
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);

			// N??o ?? necess??rio, diferente do Ignored, o ObjectMapper consegue falhar com
			// propriedades desconhecidas.
			// Estamos colocando apenas por seguran??a, palavras do pr??prio instrutor do
			// curso, Thiago.
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

			// Pega os dados de Origem e cria uma instancia de Restaurante com esses dados.
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				// Pega o campo passado e ve se ele bate com algum atributo de Restaurante, se
				// sim cria o Campo.
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				// Necess??rio para habilitar os campos privados do encapsulamento.
				field.setAccessible(true);

				// Converte o valor passado para o seu tipo certo, por Exemplo se passou inteiro
				// e espera BigDecimal, converte o inteiro em BigDecimal
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

				ReflectionUtils.setField(field, restauranteDestino, novoValor);

				// System.out.println(nomePropriedade + " = " + valorPropriedade + " = " +
				// novoValor);
			});
		} catch (IllegalArgumentException e) {

			// ExceptionUtils ?? da dependency org.apache.commons no POM
			Throwable rootCause = ExceptionUtils.getRootCause(e);

			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
	
	private void validate(Restaurante restaurante, String objectName) {
		//BindingResult armazena as viola????es de constraints de valida????o.
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
