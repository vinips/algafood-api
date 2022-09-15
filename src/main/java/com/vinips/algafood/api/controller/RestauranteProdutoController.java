package com.vinips.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vinips.algafood.api.AlgaLinks;
import com.vinips.algafood.api.model.assembler.ProdutoDTOAssembler;
import com.vinips.algafood.api.model.disassembler.ProdutoInputDisassembler;
import com.vinips.algafood.api.model.dto.ProdutoDTO;
import com.vinips.algafood.api.model.input.ProdutoInput;
import com.vinips.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.vinips.algafood.domain.model.Produto;
import com.vinips.algafood.domain.model.Restaurante;
import com.vinips.algafood.domain.repository.ProdutoRepository;
import com.vinips.algafood.domain.service.CadastroProdutoService;
import com.vinips.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi{

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoDTOAssembler produtoAssembler;

	@Autowired
	private ProdutoInputDisassembler produtoDisssembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public CollectionModel<ProdutoDTO> listar(@PathVariable Long restauranteId,
			@RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {
		Restaurante restaurante = verifyRestaurante(restauranteId);

		List<Produto> produtos = null;
		
		if(incluirInativos) {
			produtos = produtoRepository.findAllByRestaurante(restaurante);
		} else {
			produtos = produtoRepository.findAtivosByRestaurante(restaurante);
		}
			
		CollectionModel<ProdutoDTO> produtosDTO =  produtoAssembler.toCollectionModel(produtos)
				.add(algaLinks.linkToProdutos(restauranteId));
		
		return produtosDTO;
	}

	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		verifyRestaurante(restauranteId);
		Produto produto = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);

		return produtoAssembler.toModel(produto);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar(@PathVariable Long restauranteId, @Valid @RequestBody ProdutoInput produtoInput) {
		Restaurante restaurante = verifyRestaurante(restauranteId);
		Produto produto = produtoDisssembler.toDomainModel(produtoInput);
		produto.setRestaurante(restaurante);

		return produtoAssembler.toModel(cadastroProduto.salvar(produto));
	}

	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid @RequestBody ProdutoInput produtoInput) {

		verifyRestaurante(restauranteId);
		Produto produto = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);

		produtoDisssembler.copyToDomainObject(produtoInput, produto);

		return produtoAssembler.toModel(cadastroProduto.salvar(produto));
	}

	private Restaurante verifyRestaurante(Long restauranteId) {
		return cadastroRestaurante.buscarOuFalhar(restauranteId);
	}
	

}
