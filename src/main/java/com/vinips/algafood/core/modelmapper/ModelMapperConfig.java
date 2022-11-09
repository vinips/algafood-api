package com.vinips.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vinips.algafood.api.v1.model.dto.EnderecoDTO;
import com.vinips.algafood.api.v1.model.dto.ItemPedidoDTO;
import com.vinips.algafood.api.v1.model.input.ItemPedidoInput;
import com.vinips.algafood.api.v2.model.input.CidadeInputV2;
import com.vinips.algafood.domain.model.Cidade;
import com.vinips.algafood.domain.model.Endereco;
import com.vinips.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		//Associa que o Estado do Endereço vai ser uma String com apenas o nome e não um objeto completo
		//Endereco
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(), 
				(enderecoDtoDest, value) -> enderecoDtoDest.getCidade().setEstado(value));
		
		//ItemPedido
		var ItemPedidoToItemPedidoModelTypeMap = modelMapper.createTypeMap(ItemPedido.class, ItemPedidoDTO.class);

		ItemPedidoToItemPedidoModelTypeMap.<Long>addMapping(
				itemPedidoSrc -> itemPedidoSrc.getProduto().getId(), 
				(itemPedidoDtoDest, value) -> itemPedidoDtoDest.setProdutoId(value));
		
		ItemPedidoToItemPedidoModelTypeMap.<String>addMapping(
				itemPedidoSrc -> itemPedidoSrc.getProduto().getNome(), 
				(itemPedidoDtoDest, value) -> itemPedidoDtoDest.setProdutoNome(value));
		
		//Quando for converter de Input para Entidade, não seta o id. Pois é autoincrement e ta cascade.
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
		    .addMappings(mapper -> mapper.skip(ItemPedido::setId));  
		
		//No Módulo V2, pela forma que arquitetamos o input, ele seta o id do estado no id da Cidade, o que é um erro.
		//Com esse código forçamos ele a skipar o set id da cidade, vindo assim null e o JPA vai criar um novo pra ele no banco.
		modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
	    .addMappings(mapper -> mapper.skip(Cidade::setId));  
		
		
		
		return modelMapper;
	}

}
