package com.vinips.algafood.api.v2.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.v2.AlgaLinksV2;
import com.vinips.algafood.api.v2.controller.CidadeControllerV2;
import com.vinips.algafood.api.v2.model.dto.CidadeDTOV2;
import com.vinips.algafood.domain.model.Cidade;

@Component
public class CidadeDTOAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeDTOV2> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksV2 algaLinks;

	public CidadeDTOAssemblerV2() {
		super(CidadeControllerV2.class, CidadeDTOV2.class);
	}

	@Override
	public CidadeDTOV2 toModel(Cidade cidade) {
		CidadeDTOV2 cidadeDTO = createModelWithId(cidade.getId(), cidade);
				
		modelMapper.map(cidade, cidadeDTO);
		
		cidadeDTO.add(algaLinks.linkToCidades("cidades"));

		return cidadeDTO;
	}
	
	//Sobrescrevemos o método apenas para colocar o .add(link) ali no final, que é o link de toda coleção de cidades
	@Override
	public CollectionModel<CidadeDTOV2> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToCidades());
	}
	
//	public List<CidadeDTO> toCollectionDTO(List<Cidade> cidadeList) {
//		return cidadeList.stream().map(cidade -> toModel(cidade)).collect(Collectors.toList());
//	}

	
}
