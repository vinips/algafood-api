package com.vinips.algafood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vinips.algafood.api.model.assembler.FotoProdutoDTOAssembler;
import com.vinips.algafood.api.model.dto.FotoProdutoDTO;
import com.vinips.algafood.api.model.input.FotoProdutoInput;
import com.vinips.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.vinips.algafood.domain.model.FotoProduto;
import com.vinips.algafood.domain.model.Produto;
import com.vinips.algafood.domain.service.CadastroProdutoService;
import com.vinips.algafood.domain.service.CatalogoFotoProdutoService;
import com.vinips.algafood.domain.service.FotoStorageService;
import com.vinips.algafood.domain.service.FotoStorageService.FotoRecuperada;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;
	
	@Autowired
	private FotoProdutoDTOAssembler fotoProdutoAssembler;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		//Não usamos @RequestBody no FotoProdutoInput pois estamos enviando parametros de requisição e não um body. O Body da requisição nesse caso vem vazio.
		//E Tão pouco precisamos usar o @RequestParam pois eles já estão encapsulados dentro do FotoProdutoInput e o spring realiza a conversão através de chave-valor. 
		//Se não fosse um DTO e sim um único parametro, ai precisariamos do @RequestParam.
		
		Produto produto = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setProduto(produto);
		fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
		fotoProduto.setContentType(arquivo.getContentType());
		fotoProduto.setTamanho(arquivo.getSize());
		fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProduto.salvar(fotoProduto, arquivo.getInputStream());
		
		return fotoProdutoAssembler.toDTO(fotoSalva);
		
//		var nomeArquivo = UUID.randomUUID().toString() + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();
//		
//		var arquivoFoto = Path.of("E:/Development/images", nomeArquivo);
//		
//		System.out.println(fotoProdutoInput.getDescricao());
//		System.out.println(arquivoFoto);
//		System.out.println(fotoProdutoInput.getArquivo().getContentType());
//		
//		try {
//			fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
//		} catch (Exception e) {
//			throw new RuntimeException();
//		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDTO buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
		
		return fotoProdutoAssembler.toDTO(fotoProduto);
	}
	
	@GetMapping()
	public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader)
			throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);

			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);

			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypeAceitas);

			FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			//Se tem URL (que ta armazenando no S3) nós pedimos pro consumidor da API ser redirecionado para uma nova URL
			if (fotoRecuperada.temURL()) {
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			} else {
				return ResponseEntity
						.ok()
						.contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping()
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removerFotoProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		catalogoFotoProduto.excluir(restauranteId, produtoId);
	}
	

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeAceitas)
			throws HttpMediaTypeNotAcceptableException {
		
		boolean compativel = mediaTypeAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
		}
	}

}
