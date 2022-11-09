package com.vinips.algafood.api.v1.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.v1.AlgaLinks;
import com.vinips.algafood.api.v1.controller.CozinhaController;
import com.vinips.algafood.api.v1.model.dto.CozinhaDTO;
import com.vinips.algafood.domain.model.Cozinha;

@Component
public class CozinhaDTOAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public CozinhaDTOAssembler() {
		super(CozinhaController.class, CozinhaDTO.class);
	}

	@Override
	public CozinhaDTO toModel(Cozinha cozinha) {
		CozinhaDTO cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);
		
		 modelMapper.map(cozinha, cozinhaDTO);
		 
		 cozinhaDTO.add(algaLinks.linkToCozinhas("cozinhas"));
		 
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
