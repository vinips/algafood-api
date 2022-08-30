package com.vinips.algafood.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.AlgaLinks;
import com.vinips.algafood.api.controller.CidadeController;
import com.vinips.algafood.api.model.dto.CidadeDTO;
import com.vinips.algafood.domain.model.Cidade;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public CidadeDTOAssembler() {
		super(CidadeController.class, CidadeDTO.class);
	}

	@Override
	public CidadeDTO toModel(Cidade cidade) {
		
		//Fazendo assim, não precisamos utilizar o withSelfRel que comentamos logo abaixo.
		CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);
				
		modelMapper.map(cidade, cidadeDTO);
		
		//Link por Método
//		cidadeDTO.add(WebMvcLinkBuilder
//				.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscar(cidadeDTO.getId())).withSelfRel());

		cidadeDTO.add(algaLinks.linkToCidades("cidades"));

		cidadeDTO.getEstado().add(algaLinks.linkToEstado(cidadeDTO.getEstado().getId()));
		
		return cidadeDTO;
	}
	
	//Sobrescrevemos o método apenas para colocar o .add(link) ali no final, que é o link de toda coleção de cidades
	@Override
	public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToCidades());
	}
	
//	public List<CidadeDTO> toCollectionDTO(List<Cidade> cidadeList) {
//		return cidadeList.stream().map(cidade -> toModel(cidade)).collect(Collectors.toList());
//	}

	
}
