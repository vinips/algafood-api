package com.vinips.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

//Para fins de estudo
//@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Entity
public class Restaurante {
	
	//As anotações do Jackson, como JsonIgnore, etc ficam na classe de Mixin.
	//Por exemplo RestauranteMixin

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//NotBlank inclui o NotNull e o NotEmpty também.
	//@NotNull
	//@NotEmpty
	//se usar os groups, tem que usar o @Validated no Controller, em vez do @Valid
	//Aqui ta comentado pois estou usando DTO e Input para a parte de recebimento e envio de dados pela API
	//@NotBlank//(groups = Groups.CadastroRestaurante.class) 
	@Column(nullable = false)
	private String nome;

	//DecimalMin ou @PositiveOrZero são praticamente a mesma coisa.
	//@DecimalMin("0")
	//@Multiplo(numero = 5) Essa annotation foi criada por mim, apenas para fins de estudo
	//Aqui ta comentado pois estou usando DTO e Input para a parte de recebimento e envio de dados pela API
	//@NotNull
	//@PositiveOrZero
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	//Caso esteja com JsonIgnore, o ideal é colocar o Fetch para Lazy pois assim ele faz um número menor de SQL.
	//JsonIgnore ignora o atributo inteiro, JsonIgnoreProperties ignora apenas as propriedades especificadas desse atributo
	//"hibernateLazyInitializer" é o atributo que precisa ter quando o ManyToOne é setado como Lazy.
	//@JsonIgnore
	//@JsonIgnoreProperties("hibernateLazyInitializer")
	//o Valid Específica que eu quero que o Bean Validation Valid em cascata, ou seja o cozinha.id tmbm
	// @ConvertGroup converte do Default para o Alvo que você quer. Precisa usar o @Valid no controller
	//Se estivermos usando um DTO da vida na hora de receber via http, não precisamos do ConvertGroup, colocamos direto o NotNull no atributo do DTO. No caso usamos o RestauranteInput no projeto
	//Aqui ta comentado pois estou usando DTO e Input para a parte de recebimento e envio de dados pela API
	//@Valid
	//@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	//@NotNull
	@ManyToOne //(fetch = FetchType.LAZY)
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	private Endereco endereco;
	
	private boolean ativo = Boolean.TRUE;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")//datetime é para não criar a precisão de milisegundos
	private OffsetDateTime dataCadastro;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")//datetime é para não criar a precisão de milisegundos
	private OffsetDateTime dataAtualizacao;

	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", 
		joinColumns = @JoinColumn(name = "restaurante_id"), 
		inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>();
	
	//Não necessariamente precisa ter, pq aqui estou fazendo Bi-dimensional apenas para fins de estudos.
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();	

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
	
	public Set<FormaPagamento> getFormasPagamento() {
		return formasPagamento;
	}

	public void setFormasPagamento(Set<FormaPagamento> formasPagamento) {
		this.formasPagamento = formasPagamento;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public OffsetDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(OffsetDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public OffsetDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(OffsetDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
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

	@Override
	public String toString() {
		return "Restaurante [id=" + id + ", nome=" + nome + ", taxaFrete=" + taxaFrete + ", cozinha=" + cozinha
				+ ", endereco=" + endereco + ", ativo=" + ativo + ", dataCadastro=" + dataCadastro
				+ ", dataAtualizacao=" + dataAtualizacao + ", formasPagamento=" + formasPagamento + ", produtos="
				+ produtos + "]";
	}
	
	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}
	
	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}
	
	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}
	
	

}
