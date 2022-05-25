	package com.vinips.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.vinips.algafood.domain.event.PedidoCanceladoEvent;
import com.vinips.algafood.domain.event.PedidoConfirmadoEvent;
import com.vinips.algafood.domain.exception.NegocioException;

//AbstractAggregateRoot é para podermos usar domain events e registrar eventos com ele.
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigo;
	
	@Column(nullable = false)
	private BigDecimal subtotal;
	
	@Column(nullable = false)
	private BigDecimal taxaFrete;

	@Column(nullable = false)
	private BigDecimal valorTotal;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")//datetime é para não criar a precisão de milisegundos
	private OffsetDateTime dataCriacao;
	
	@Column(columnDefinition = "datetime")//datetime é para não criar a precisão de milisegundos
	private OffsetDateTime dataConfirmacao;
	
	@Column(columnDefinition = "datetime")//datetime é para não criar a precisão de milisegundos
	private OffsetDateTime dataCancelamento;
	
	@Column(columnDefinition = "datetime")//datetime é para não criar a precisão de milisegundos
	private OffsetDateTime dataEntrega;
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	@ManyToOne 
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;
	
	@ManyToOne 
	@JoinColumn(name = "restaurante_id", nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forma_pagamento_id", nullable = false)
	private FormaPagamento formaPagamento;
	
	//Não necessariamente precisa ter, pq aqui estou fazendo Bi-dimensional apenas para fins de estudos.
	// CascadeType.ALL é para quando salvarmos um pedido, salvarmos em cascata os itens também.
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
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

	public StatusPedido getStatus() {
		return status;
	}

	private void setStatus(StatusPedido novoStatus) {
		if(getStatus().naoPodeAlterarPara(novoStatus)) {
			throw new NegocioException(String.format("Status do pedido '%s' não pode ser alterado de '%s' para '%s'",
					getCodigo(), getStatus().getDescricao(), novoStatus.getDescricao()));
		}
		this.status = novoStatus;
	}

	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", codigo=" + codigo + ", subtotal=" + subtotal + ", taxaFrete=" + taxaFrete
				+ ", valorTotal=" + valorTotal + ", dataCriacao=" + dataCriacao + ", dataConfirmacao=" + dataConfirmacao
				+ ", dataCancelamento=" + dataCancelamento + ", dataEntrega=" + dataEntrega + ", status=" + status
				+ ", enderecoEntrega=" + enderecoEntrega + ", cliente=" + cliente + ", restaurante=" + restaurante
				+ ", formaPagamento=" + formaPagamento + ", itens=" + itens + "]";
	}
	
	//Antes de Persistir a entidade no banco, o Hibernate roda esse código automaticamente
	@PrePersist
	private void gerarCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}

	public void calcularValorTotal() {
		getItens().forEach(ItemPedido::calcularPrecoTotal);
		
	    this.subtotal = getItens().stream()
	        .map(item -> item.getPrecoTotal())
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
	    
	    this.valorTotal = this.subtotal.add(this.taxaFrete);
	}
	
	public void confirmar() {
		setStatus(StatusPedido.CONFIRMADO);
		setDataConfirmacao(OffsetDateTime.now());
		
		
		//Aqui ainda não dispara o evento, apenas registra ele para que seja disparado quando
		//a Entidade Pedido for salva no repositório;
		registerEvent(new PedidoConfirmadoEvent(this));
		
	}
	
	public void entregar() {
		setStatus(StatusPedido.ENTREGUE);
		setDataEntrega(OffsetDateTime.now());
	}
	
	public void cancelar() {
		setStatus(StatusPedido.CANCELADO);
		setDataCancelamento(OffsetDateTime.now());
		
		registerEvent(new PedidoCanceladoEvent(this));
	}

}
