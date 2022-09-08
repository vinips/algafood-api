package com.vinips.algafood.api.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;

@Relation(collectionRelation = "pedidos")
public class PedidoResumoDTO extends RepresentationModel<PedidoResumoDTO> {
	
	@ApiModelProperty(example = "f55a5397-abba-11ec-9203-b42e99ed2795")
	private String codigo;
	
	@ApiModelProperty(example = "12.00")
	private BigDecimal subtotal;
	
	@ApiModelProperty(example = "2.90")
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(example = "14.90")
	private BigDecimal valorTotal;
	
	@ApiModelProperty(example = "CRIADO")
	private String status;
	
	@ApiModelProperty(example = "2022-07-15T04:38:55Z")
	private OffsetDateTime dataCriacao;
	
	private RestauranteApenasNomeDTO restaurante;
	private UsuarioDTO cliente;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTaxaFrete() {
		return taxaFrete;
	}

	public void setTaxaFrete(BigDecimal taxaFrete) {
		this.taxaFrete = taxaFrete;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public OffsetDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(OffsetDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UsuarioDTO getCliente() {
		return cliente;
	}

	public void setCliente(UsuarioDTO cliente) {
		this.cliente = cliente;
	}

	public RestauranteApenasNomeDTO getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(RestauranteApenasNomeDTO restaurante) {
		this.restaurante = restaurante;
	}
	
}
