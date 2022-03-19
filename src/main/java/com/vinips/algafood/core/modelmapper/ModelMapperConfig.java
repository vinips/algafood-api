package com.vinips.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vinips.algafood.api.model.dto.EnderecoDTO;
import com.vinips.algafood.api.model.dto.ItemPedidoDTO;
import com.vinips.algafood.domain.model.Endereco;
import com.vinips.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		//Associa que o Estado do Endereço vai ser uma String com apenas o nome e não um objeto completo
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(), 
				(enderecoDtoDest, value) -> enderecoDtoDest.getCidade().setEstado(value));
		
		var ItemPedidoToItemPedidoModelTypeMap = modelMapper.createTypeMap(ItemPedido.class, ItemPedidoDTO.class);
		
		ItemPedidoToItemPedidoModelTypeMap.<Long>addMapping(
				itemPedidoSrc -> itemPedidoSrc.getProduto().getId(), 
				(itemPedidoDtoDest, value) -> itemPedidoDtoDest.setProdutoId(value));
		
		ItemPedidoToItemPedidoModelTypeMap.<String>addMapping(
				itemPedidoSrc -> itemPedidoSrc.getProduto().getNome(), 
				(itemPedidoDtoDest, value) -> itemPedidoDtoDest.setProdutoNome(value));
		
		return modelMapper;
	}

}
