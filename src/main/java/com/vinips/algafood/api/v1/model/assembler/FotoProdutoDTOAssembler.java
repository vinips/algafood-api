package com.vinips.algafood.api.v1.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.v1.AlgaLinks;
import com.vinips.algafood.api.v1.controller.RestauranteFormaPagamentoController;
import com.vinips.algafood.api.v1.model.dto.FotoProdutoDTO;
import com.vinips.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoDTOAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public FotoProdutoDTOAssembler() {
		super(RestauranteFormaPagamentoController.class, FotoProdutoDTO.class);
	}

	public FotoProdutoDTO toModel(FotoProduto fotoProduto) {
		FotoProdutoDTO fotoProdutoDTO = createModelWithId(fotoProduto.getProduto().getId(), fotoProduto,
				fotoProduto.getRestauranteId());
		
		fotoProdutoDTO.add(algaLinks.linkToProduto(fotoProduto.getRestauranteId(), fotoProduto.getId(), "produto"));
		
		modelMapper.map(fotoProduto, fotoProdutoDTO);
		
		return fotoProdutoDTO;
	}
}
