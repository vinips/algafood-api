package com.vinips.algafood.api.v1.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.vinips.algafood.api.v1.AlgaLinks;
import com.vinips.algafood.api.v1.controller.EstadoController;
import com.vinips.algafood.api.v1.model.dto.EstadoDTO;
import com.vinips.algafood.domain.model.Estado;

@Component
public class EstadoDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO>{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public EstadoDTOAssembler() {
		super(EstadoController.class, EstadoDTO.class);
	}

	@Override
	public EstadoDTO toModel(Estado estado) {
		EstadoDTO estadoDTO = createModelWithId(estado.getId(), estado);
		
		modelMapper.map(estado, estadoDTO);
		
		estadoDTO.add(algaLinks.linkToEstados("estados"));
		
		return estadoDTO;
	}
	
	@Override
	public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToEstados());
	}
	
//	public List<EstadoDTO> toCollectionDTO(List<Estado> estadoList) {
//		return estadoList.stream().map(estado -> toDTO(estado)).collect(Collectors.toList());
//	}

	
}
