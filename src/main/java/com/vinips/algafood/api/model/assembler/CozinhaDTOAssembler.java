package com.vinips.algafood.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.controller.CozinhaController;
import com.vinips.algafood.api.model.dto.CozinhaDTO;
import com.vinips.algafood.domain.model.Cozinha;

@Component
public class CozinhaDTOAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO>{

	@Autowired
	private ModelMapper modelMapper;

	public CozinhaDTOAssembler() {
		super(CozinhaController.class, CozinhaDTO.class);
	}

	@Override
	public CozinhaDTO toModel(Cozinha cozinha) {
		CozinhaDTO cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);
		
		 modelMapper.map(cozinha, cozinhaDTO);
		 
		 cozinhaDTO.add(WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel("cozinhas"));
		 
		 return cozinhaDTO;
	}
	
//	@Override
//	public CollectionModel<CozinhaDTO> toCollectionModel(Iterable<? extends Cozinha> entities) {
//		return super.toCollectionModel(entities);
//	}
	
//	public List<CozinhaDTO> toCollectionDTO(List<Cozinha> cozinhaList) {
//		return cozinhaList.stream().map(cozinha -> toDTO(cozinha)).collect(Collectors.toList());
//	}
	
}
