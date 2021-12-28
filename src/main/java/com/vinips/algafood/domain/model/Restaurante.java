package com.vinips.algafood.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", 
		joinColumns = @JoinColumn(name = "restaurante_id"), 
		inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getTaxaFrete() {
		return taxaFrete;
	}

	public void setTaxaFrete(BigDecimal taxaFrete) {
		this.taxaFrete = taxaFrete;
	}

	public Cozinha getCozinha() {
		return cozinha;
	}

	public void setCozinha(Cozinha cozinha) {
		this.cozinha = cozinha;
	}
	
	public List<FormaPagamento> getFormasPagamento() {
		return formasPagamento;
	}

	public void setFormasPagamento(List<FormaPagamento> formasPagamento) {
		this.formasPagamento = formasPagamento;
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
		Restaurante other = (Restaurante) obj;
		return Objects.equals(id, other.id);
	}

	public String toString() {
		return "Restaurante = [Id = " + this.id + "], [Nome = " + this.nome + "], [TaxaFrete = " + this.taxaFrete
				+ "], [Cozinha = " + this.cozinha + "]";
	}

	

}
