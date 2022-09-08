package com.vinips.algafood.api.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;

@Relation(collectionRelation = "pedidos")
public class PedidoDTO extends RepresentationModel<PedidoDTO>{
	
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
	
	@ApiModelProperty(example = "2022-07-15T05:55:55Z")
	private OffsetDateTime dataConfirmacao;
	
	@ApiModelProperty(example = "2022-07-15T05:59:55Z")
	private OffsetDateTime dataEntrega;
	
	@ApiModelProperty(example = "2022-07-15T04:58:55Z")
	private OffsetDateTime dataCancelamento;
	
	private RestauranteApenasNomeDTO restaurante;
	
	private UsuarioDTO cliente;
	
	private FormaPagamentoDTO formaPagamento;
	
	private EnderecoDTO enderecoEntrega;
	
	private List<ItemPedidoDTO> itens;

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

	public OffsetDateTime getDataConfirmacao() {
		return dataConfirmacao;
	}

	public void setDataConfirmacao(OffsetDateTime dataConfirmacao) {
		this.dataConfirmacao = dataConfirmacao;
	}

	public OffsetDateTime getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(OffsetDateTime dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public OffsetDateTime getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(OffsetDateTime dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EnderecoDTO getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(EnderecoDTO enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
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

	public FormaPagamentoDTO getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamentoDTO formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public List<ItemPedidoDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoDTO> itens) {
		this.itens = itens;
	}
	
}
